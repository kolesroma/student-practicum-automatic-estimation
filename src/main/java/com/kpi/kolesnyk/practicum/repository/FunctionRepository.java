package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRepository extends JpaRepository<FunctionEntity, Long> {
}
