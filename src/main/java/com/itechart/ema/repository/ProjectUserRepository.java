package com.itechart.ema.repository;

import com.itechart.ema.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, UUID> {

    @Modifying
    @Query(value = "DELETE FROM project_user " +
            "WHERE project_id = :projectId " +
            "AND user_id = :userId ",
            nativeQuery = true)
    void deleteOneByProjectIdAndUserId(@Param("projectId") UUID projectId,
                                       @Param("userId") UUID userId);

    @Modifying
    @Query(value = "DELETE FROM project_user " +
            "WHERE project_id = :projectId ",
            nativeQuery = true)
    void deleteAllByProjectId(@Param("projectId") UUID projectId);

    @Modifying
    @Query(value = "DELETE FROM project_user " +
            "WHERE user_id = :userId ",
            nativeQuery = true)
    void deleteAllByUserId(@Param("userId") UUID userId);

}
