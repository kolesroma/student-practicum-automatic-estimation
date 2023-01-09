package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.QualityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityRepository extends JpaRepository<QualityEntity, Long> {
}
