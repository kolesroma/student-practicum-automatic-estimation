package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
