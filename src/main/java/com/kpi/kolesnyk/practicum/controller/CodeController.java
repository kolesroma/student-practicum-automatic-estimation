package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.exception.ResourceNotFoundException;
import com.kpi.kolesnyk.practicum.service.CodeRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeRunner codeRunner;

    @PostMapping("/result/{taskId}")
    public String result(@RequestParam String code,
                         @PathVariable Long taskId,
                         Principal principal,
                         Model model) {
        model.addAttribute("mark", processWithTimeout(code, taskId, principal));
        return "result";
    }

    private Map<String, Integer> processWithTimeout(String code, Long taskId, Principal principal) {
        try {
            var executorService = Executors.newSingleThreadExecutor();
            var marks = executorService
                    .submit(() -> codeRunner.estimate(principal, taskId, code))
                    .get(12, TimeUnit.SECONDS);
            executorService.shutdown();
            return marks;
        } catch (InterruptedException | ExecutionException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (TimeoutException e) {
            throw new AsyncRequestTimeoutException();
        }
    }
}
