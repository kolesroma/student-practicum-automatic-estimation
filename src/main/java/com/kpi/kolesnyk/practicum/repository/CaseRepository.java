package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<CaseEntity, Long> {
}
