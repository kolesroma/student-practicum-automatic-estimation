package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
}
