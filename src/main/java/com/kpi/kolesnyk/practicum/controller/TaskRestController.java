package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TaskRestController {
    private final TaskService taskService;

    @PostMapping(value = "/tasks/create")
    public void create(@Valid @RequestBody TaskCreationDto task,
                       Principal principal) {
        System.out.println(task);
        taskService.create(task, principal);
    }
}
