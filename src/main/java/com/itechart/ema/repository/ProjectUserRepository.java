package com.itechart.ema.repository;

import com.itechart.ema.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, UUID> {
}
