package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.service.CodeRunner;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CodeRunnerPython implements CodeRunner {
    @Override
    public void run(String fileName) {
        try (PythonInterpreter python = new PythonInterpreter()) {
            String code = String.join("\n", Files.readAllLines(Path.of(fileName)));
            python.exec(code);

            PyFunction function = python.get("delete_brackets", PyFunction.class);

            System.out.println("res: " + function.__call__(new PyString("sss")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CodeRunnerPython codeRunnerPython = new CodeRunnerPython();
        String fileName = "/home/roman/script.py";
        codeRunnerPython.run(fileName);
    }
}
