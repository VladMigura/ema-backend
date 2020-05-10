package com.itechart.ema.repository;

import com.itechart.ema.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    @Query(value = "SELECT(EXISTS(SELECT * FROM task " +
            "WHERE id = :taskId " +
            "AND deleted_at IS NULL)) ",
            nativeQuery = true)
    boolean existsById(@Param("taskId") UUID taskId);

    @Query(value = "SELECT * FROM task " +
            "WHERE id = :taskId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<TaskEntity> findOneById(@Param("taskId") UUID taskId);

    @Query(value = "SELECT * FROM task " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at DESC ",
            nativeQuery = true)
    List<TaskEntity> findAll();

    @Query(value = "SELECT * FROM task " +
            "WHERE dev_owner_id = :userId " +
            "AND deleted_at IS NULL " +
            "ORDER BY created_at DESC ",
            nativeQuery = true)
    List<TaskEntity> findAllByDevOwnerId(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM task " +
            "WHERE project_id = :projectId " +
            "AND deleted_at IS NULL " +
            "ORDER BY created_at DESC ",
            nativeQuery = true)
    List<TaskEntity> findAllByProjectId(@Param("projectId") UUID projectId);

    @Modifying
    @Query(value = "UPDATE task " +
            "SET deleted_at = now() " +
            "WHERE id = :taskId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("taskId") UUID taskId);

}
