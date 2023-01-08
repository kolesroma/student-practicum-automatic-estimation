package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Long> {
    List<MarkEntity> findAllByUserId(Long userId);
}
