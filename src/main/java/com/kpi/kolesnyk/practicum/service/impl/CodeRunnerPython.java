package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.ParamEntity;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.CodeRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeRunnerPython implements CodeRunner {
    private static final int PERCENT_COEFFICIENT = 100;

    private final TaskRepository taskRepository;

    @Override
    public String estimate(Long taskId, String code) {
        var task = taskRepository.findById(taskId)
                .orElseThrow();
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            var userFunction = python.get(task.getFunction().getName(), PyFunction.class);
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
            return Math.round(((double) mark) * PERCENT_COEFFICIENT / maxMark) + "%";
        } catch (PyException e) {
            return e.traceback != null
                    ? e.traceback.dumpStack() + e.getMessage()
                    : e.getMessage();
        }
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
