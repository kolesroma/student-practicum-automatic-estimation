package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, Long> {
}
