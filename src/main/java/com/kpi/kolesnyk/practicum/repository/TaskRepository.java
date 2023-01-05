package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
