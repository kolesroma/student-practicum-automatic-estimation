package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.model.GroupEntity;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String root(Principal principal,
                       Model model) {
        return home(principal, model);
    }

    @GetMapping("/home")
    public String home(Principal principal,
                       Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/info")
    public String info(Principal principal,
                       Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "info";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", UserEntity.builder()
                .group(new GroupEntity())
                .build());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserEntity userForm,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(userForm);
        return "redirect:/login?success";
    }
}
