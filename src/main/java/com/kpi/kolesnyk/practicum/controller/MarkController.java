package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MarkController {
    private final MarkService markService;

    @GetMapping("/marks")
    public String findAll(Principal principal,
                         Model model) {
        model.addAttribute("marks", markService.findAll(principal));
        return "marks";
    }
}
