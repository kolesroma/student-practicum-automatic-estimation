package com.kpi.kolesnyk.practicum.service;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.model.TaskEntity;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    List<TaskEntity> findAll(Principal principal);
    TaskEntity findById(Long taskId);
    void create(TaskCreationDto taskDto, Principal principal);
    void update(TaskCreationDto taskDto, Long taskId, Principal principal);
}
