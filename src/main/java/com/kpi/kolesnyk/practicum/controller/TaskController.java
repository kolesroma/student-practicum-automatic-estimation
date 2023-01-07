package com.kpi.kolesnyk.practicum.controller;

import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        return "task";
    }

    @GetMapping("/tasks/create")
    public String create() {
        return "createTask";
    }
}
