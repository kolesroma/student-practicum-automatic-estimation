package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.service.CodeRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeRunner codeRunner;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/result/{taskId}")
    public String result(@RequestParam String code,
                         @PathVariable Long taskId,
                         Model model) {
        model.addAttribute("mark", codeRunner.estimate(taskId, code));
        return "result";
    }
}
