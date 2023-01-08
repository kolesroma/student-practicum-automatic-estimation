package com.kpi.kolesnyk.practicum.repository;

import com.kpi.kolesnyk.practicum.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
