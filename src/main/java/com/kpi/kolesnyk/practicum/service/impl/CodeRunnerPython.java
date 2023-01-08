package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.ParamEntity;
import com.kpi.kolesnyk.practicum.model.TaskEntity;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.CodeRunner;
import com.kpi.kolesnyk.practicum.service.MarkService;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeRunnerPython implements CodeRunner {
    private static final int PERCENT_COEFFICIENT = 100;

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final MarkService markService;

    @Override
    @Transactional
    public String estimate(Principal principal, Long taskId, String code) {
        var user = userService.findByUsername(principal.getName());
        if (!hasUserAccessToTask(user, taskId)) {
            throw new RuntimeException("You have no access to this task");
        }
        var task = taskRepository.findById(taskId)
                .orElseThrow();
        String functionName = task.getFunction().getName();
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            var userFunction = python.get(functionName, PyFunction.class);
            if (userFunction == null) {
                return "cannot find function with name " + functionName;
            }
            int mark = getMarkForTaskWithUserCode(task, userFunction);
            markService.create(taskId, user.getId(), mark);
            return mark + "%";
        } catch (PyException e) {
            return e.traceback != null
                    ? e.traceback.dumpStack() + e.getMessage()
                    : e.getMessage();
        }
    }

    private boolean hasUserAccessToTask(UserEntity user, Long taskId) {
        return true;
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
