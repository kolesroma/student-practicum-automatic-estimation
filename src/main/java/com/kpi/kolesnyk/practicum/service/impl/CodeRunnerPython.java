package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.ParamEntity;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.CodeRunner;
import lombok.RequiredArgsConstructor;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CodeRunnerPython implements CodeRunner {
    private final TaskRepository taskRepository;

    @Override
    public String estimate(Long taskId, String code) {
        var task = taskRepository.findById(taskId)
                .orElseThrow();

        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            PyFunction userFunction = python.get(task.getFunction().getName(), PyFunction.class);

            return String.valueOf(userFunction.__call__(provideFunctionParameters(task.getFunction().getParams())));
        } catch (PyException e) {
            return e.traceback.dumpStack() + e.getMessage();
        }
    }

    private PyObject[] provideFunctionParameters(Collection<ParamEntity> params) {
        List<PyObject> pyObjects = new ArrayList<>();
        params.forEach(paramEntity -> {
            PyObject pyObject = null;
            if ("number".equals(paramEntity.getType())) {
                pyObject = new PyFloat(1f);
            } else if ("string".equals(paramEntity.getType())) {
                pyObject = new PyString("test string");
            }
            pyObjects.add(pyObject);
        });
        return pyObjects.toArray(new PyObject[0]);
    }
}
