package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.ParamEntity;
import com.kpi.kolesnyk.practicum.model.TaskEntity;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.service.CodeRunner;
import com.kpi.kolesnyk.practicum.service.MarkService;
import com.kpi.kolesnyk.practicum.service.TaskService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeRunnerPython implements CodeRunner {
    private static final int PERCENT_COEFFICIENT = 100;

    private final TaskService taskService;
    private final UserService userService;
    private final MarkService markService;

    @Override
    @Transactional
    public String estimate(Principal principal, Long taskId, String code) {
        var user = userService.findByUsername(principal.getName());
        var task = taskService.findAll(principal).stream()
                .filter(taskEntity -> taskId.equals(taskEntity.getId()))
                .findAny()
                .orElseThrow(TASK_NOT_FOUND);
        String functionName = task.getFunction().getName();
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            var userFunction = python.get(functionName, PyFunction.class);
            if (userFunction == null) {
                return "cannot find function with name " + functionName;
            }
            return saveTotalAndGetMarksMap(task, userFunction, user) + "";
        } catch (PyException e) {
            return e.traceback != null
                    ? e.traceback.dumpStack() + e.getMessage()
                    : e.getMessage();
        }
    }

    private Map<String, Integer> saveTotalAndGetMarksMap(TaskEntity task, PyFunction userFunction, UserEntity user) {
        var marks = new LinkedHashMap<>(Map.of("codeMark", getMarkForTaskWithUserCode(task, userFunction),
                "linterMark", getLinterMark(task, userFunction),
                "bigNotationMark", getBigNotationMark(task, userFunction)));
        final int total = calculateTotalMark(task, marks);
        marks.put("total", total);
        markService.create(task.getId(), user.getId(), total);
        log.info("got mark:" + user.getUsername() + ":" + marks);
        return marks;
    }

    private int calculateTotalMark(TaskEntity task, Map<String, Integer> marks) {
        var quality = task.getQuality();
        return (quality.getCaseCoef() * marks.get("codeMark")
                + quality.getLinterCoef() * marks.get("linterMark")
                + quality.getComplexityCoef() * marks.get("bigNotationMark")) / PERCENT_COEFFICIENT;
    }

    private int getLinterMark(TaskEntity task, PyFunction userFunction) {
        return 30;
    }

    private int getBigNotationMark(TaskEntity task, PyFunction userFunction) {
        return 50;
    }

    private int getMarkForTaskWithUserCode(TaskEntity task, PyFunction userFunction) {
        var funcArgs = task.getFunction().getParams();
        var cases = funcArgs.get(0).getCases();
        final int maxMark = cases.size();
        int mark = 0;
        for (int i = 0; i < maxMark; i++) {
            String expected = cases.get(i).getResult().getExpected();
            String actual = String.valueOf(userFunction.__call__(getFunctionInputWithCaseIndex(funcArgs, i)));
            log.info("EXPECTED::" + expected + "\tACTUAL::" + actual);
            if (expected.equals(actual)) {
                mark++;
            }
        }
        return (int) Math.round(((double) mark) * PERCENT_COEFFICIENT / maxMark);
    }

    private PyObject[] getFunctionInputWithCaseIndex(List<ParamEntity> funcArgs, int index) {
        final int size = funcArgs.size();
        var pyObjects = new PyObject[size];
        for (int i = 0; i < size; i++) {
            PyObject pyObject;
            var paramEntity = funcArgs.get(i);
            var value = paramEntity.getCases().get(index).getValue();
            if (paramEntity.getType().equals("number")) {
                pyObject = new PyFloat(Float.parseFloat(value));
            } else {
                pyObject = new PyString(value);
            }
            pyObjects[i] = pyObject;
        }
        return pyObjects;
    }
}
