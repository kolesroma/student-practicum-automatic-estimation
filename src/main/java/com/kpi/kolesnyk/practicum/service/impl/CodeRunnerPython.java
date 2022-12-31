package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.service.CodeRunner;
import org.python.core.PyException;
import org.python.core.PyFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

@Service
public class CodeRunnerPython implements CodeRunner {
    @Override
    public String run(String code) {
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            PyFunction function = python.get("delete_brackets", PyFunction.class);
            return String.valueOf(function.__call__(new PyString("sss")));
        } catch (PyException e) {
            return e.traceback.dumpStack() + e.getMessage();
        }
    }
}
