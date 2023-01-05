package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
