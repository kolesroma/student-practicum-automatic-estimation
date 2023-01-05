package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.TaskEntity;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow();
    }
}
