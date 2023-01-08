package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByOwnerId(Long ownerId);
}
