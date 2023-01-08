package com.kpi.kolesnyk.practicum.service;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.model.TaskEntity;

import java.util.List;

public interface TaskService {
    List<TaskEntity> findAll();
    TaskEntity findById(Long taskId);
    void create(TaskCreationDto taskDto);
}
