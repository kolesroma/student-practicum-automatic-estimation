package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TaskControllerRest {
    private final TaskService taskService;

    @PostMapping("/tasks")
    public void create(@Valid @RequestBody TaskCreationDto task,
                       Model model) {
        taskService.create(task);
    }
}
