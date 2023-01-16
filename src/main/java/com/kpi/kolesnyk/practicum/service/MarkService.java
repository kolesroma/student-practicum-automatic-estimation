package com.kpi.kolesnyk.practicum.service;

import com.kpi.kolesnyk.practicum.model.MarkEntity;

import java.security.Principal;
import java.util.List;

public interface MarkService {
    void create(Long taskId, Long userId, Integer score);
    List<MarkEntity> findAll(Principal principal);
    List<MarkEntity> findAll(Long userId);
}
