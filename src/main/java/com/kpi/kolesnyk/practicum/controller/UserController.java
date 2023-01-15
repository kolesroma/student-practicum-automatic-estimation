package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.exception.ResourceNotFoundException;
import com.kpi.kolesnyk.practicum.model.GroupEntity;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.NO_ACCESS;

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

    @GetMapping("/users")
    public String findAll(Principal principal, Model model) {
        var user = userService.findByUsername(principal.getName());
        if ("ROLE_ADMIN".equals(user.getRole().getAuthority())) {
            model.addAttribute("users", userService.findAll());
            return "users";
        } else {
            throw new ResourceNotFoundException("no access");
        }
    }

    @PostMapping("/users/ban/{userId}")
    public String ban(Principal principal,
                      @PathVariable Long userId) {
        var user = userService.findByUsername(principal.getName());
        if ("ROLE_ADMIN".equals(user.getRole().getAuthority())) {
            userService.ban(userId);
        } else {
            throw new ResourceNotFoundException("no access");
        }
        return "redirect:/users?success";
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
