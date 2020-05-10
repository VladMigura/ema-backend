package com.itechart.ema.repository;

import com.itechart.ema.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

    @Query(value = "SELECT(EXISTS(SELECT * FROM project " +
            "WHERE id = :projectId " +
            "AND deleted_at IS NULL)) ",
            nativeQuery = true)
    boolean existsById(@Param("projectId") UUID projectId);

    @Query(value = "SELECT * FROM project " +
            "WHERE id = :projectId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<ProjectEntity> findOneById(@Param("projectId") UUID projectId);

    @Query(value = "SELECT * FROM project " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY name ",
            nativeQuery = true)
    List<ProjectEntity> findAll();

    @Query(value = "SELECT * FROM project " +
            "LEFT JOIN project_user pu ON project.id = pu.project_id " +
            "WHERE (pu.user_id = :userId OR manager_id = :userId OR scrum_master_id = :userId) " +
            "AND deleted_at IS NULL " +
            "ORDER BY name ",
            nativeQuery = true)
    List<ProjectEntity> findAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "UPDATE project " +
            "SET deleted_at = now() " +
            "WHERE id = :projectId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("projectId") UUID projectId);

}
