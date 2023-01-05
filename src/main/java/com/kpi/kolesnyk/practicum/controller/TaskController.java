package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public String findAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/tasks/{taskId}")
    public String findById(@PathVariable Long taskId,
                           Model model) {
        model.addAttribute("task", taskService.findById(taskId));
        return "home";
    }

//    @PostMapping("/tasks")
    public void create(@Valid @RequestBody TaskCreationDto task,
                       Model model) {
        taskService.create(task);
    }
}
