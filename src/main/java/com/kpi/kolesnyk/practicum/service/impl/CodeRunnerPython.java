package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.service.CodeRunner;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CodeRunnerPython implements CodeRunner {
    @Override
    @Deprecated
    public String estimate(Integer taskId, String code) {
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            PyFunction function = python.get("functionName", PyFunction.class);

            return String.valueOf(function.__call__());
        } catch (PyException e) {
            return e.traceback.dumpStack() + e.getMessage();
        }
    }

    private List<PyObject> getPyObjects(Map<String, String> params) {
        List<PyObject> pyObjects = new ArrayList<>();
        params.forEach((key, value) -> {
            PyObject pyObject = null;
            if ("number".equals(value)) {
                pyObject = new PyFloat(Float.parseFloat(key));
            } else if ("string".equals(value)) {
                pyObject = new PyString(key);
            }
            pyObjects.add(pyObject);
        });
        return pyObjects;
    }
}
