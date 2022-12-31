package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.service.CodeRunner;
import org.python.core.PyFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

@Service
public class CodeRunnerPython implements CodeRunner {
    @Override
    public void run(String code) {
        try (PythonInterpreter python = new PythonInterpreter()) {
            python.exec(code);
            PyFunction function = python.get("delete_brackets", PyFunction.class);
            System.out.println("res: " + function.__call__(new PyString("sss")));
        }
    }

    public static void main(String[] args) {
        CodeRunnerPython codeRunnerPython = new CodeRunnerPython();
        String code = "def delete_brackets(s):\n" +
                "    return s";
        codeRunnerPython.run(code);
    }
}
