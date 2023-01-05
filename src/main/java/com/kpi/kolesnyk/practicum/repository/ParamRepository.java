package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.ParamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamRepository extends JpaRepository<ParamEntity, Long> {
}
