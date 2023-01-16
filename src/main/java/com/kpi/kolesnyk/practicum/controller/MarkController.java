package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.exception.ResourceNotFoundException;
import com.kpi.kolesnyk.practicum.service.MarkService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MarkController {
    private final MarkService markService;
    private final UserService userService;

    @GetMapping("/marks")
    public String findAll(Principal principal,
                          Model model) {
        model.addAttribute("marks", markService.findAll(principal));
        return "marks";
    }

    @GetMapping("/marks/{userId}")
    public String findAllFilter(@PathVariable Long userId,
                                Principal principal,
                                Model model) {
        var user = userService.findByUsername(principal.getName());
        if ("ROLE_TEACHER".equals(user.getRole().getAuthority())) {
            model.addAttribute("marks", markService.findAll(userId));
        } else {
            throw new ResourceNotFoundException("no access");
        }
        return "marks";
    }
}
