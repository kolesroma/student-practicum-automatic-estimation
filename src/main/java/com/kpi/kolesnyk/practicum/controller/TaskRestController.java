package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.service.TaskService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TaskRestController {
    private final TaskService taskService;

    @PostMapping(value = "/tasks/create")
    public void create(@Valid @RequestBody TaskCreationDto taskJson) {
        System.out.println(taskJson);
        taskService.create(taskJson);
    }
}
