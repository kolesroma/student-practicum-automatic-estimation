package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.service.TaskService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;


@RestController
@RequiredArgsConstructor
public class TaskRestController {
    private final TaskService taskService;
    private final UserService userService;

    @PostMapping(value = "/tasks/create")
    public void create(@Valid @RequestBody TaskCreationDto task,
                       Principal principal) {
        var user = userService.findByUsername(principal.getName());
        if ("ROLE_TEACHER".equals(user.getRole().getAuthority())) {
            taskService.create(task, principal);
        } else {
            NO_ACCESS.get();
        }
    }
}
