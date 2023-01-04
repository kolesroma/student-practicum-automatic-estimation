package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.CaseEntity;
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
import java.util.Collection;
import java.util.List;

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
            var userFunction = python.get(task.getFunction().getName(), PyFunction.class);

            var funcArgs = (List<ParamEntity>) task.getFunction().getParams();

            int size = funcArgs.get(0).getCases().size();
            for (int i = 0; i < size; i++) {
                var paramEntity = funcArgs.get(0);
                var expected = (((List<CaseEntity>) paramEntity.getCases()).get(i).getResult()).getExpected();

                String actual = String.valueOf(
                        userFunction.__call__(getFunctionInputWithCaseIndex(funcArgs, i))
                );

                System.out.println("EXPECTED:: " + expected);
                System.out.println("ACTUAL:: " + actual);
            }

            return "actual";
        } catch (PyException e) {
            return e.traceback.dumpStack() + e.getMessage();
        }
    }

    private PyObject[] getFunctionInputWithCaseIndex(Collection<ParamEntity> funcArgs, int index) {
        List<PyObject> pyObjects = new ArrayList<>();
        funcArgs.forEach(paramEntity -> {
            PyObject pyObject = null;
            var caseWithIndex = ((List<CaseEntity>) paramEntity.getCases()).get(index);
            if ("number".equals(paramEntity.getType())) {
                pyObject = new PyFloat(Float.parseFloat(caseWithIndex.getValue()));
            } else if ("string".equals(paramEntity.getType())) {
                pyObject = new PyString(caseWithIndex.getValue());
            }
            pyObjects.add(pyObject);
        });
        return pyObjects.toArray(new PyObject[0]);
    }
}
