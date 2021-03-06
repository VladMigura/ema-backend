package com.itechart.ema.repository;

import com.itechart.ema.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {

    @Query(value = "SELECT(EXISTS(SELECT * FROM team " +
            "WHERE id = :teamId " +
            "AND deleted_at IS NULL)) ",
            nativeQuery = true)
    boolean existsById(@Param("teamId") UUID teamId);

    @Query(value = "SELECT * FROM team " +
            "WHERE id = :teamId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<TeamEntity> findOneById(@Param("teamId") UUID teamId);

    @Query(value = "SELECT * FROM team " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY name ",
            nativeQuery = true)
    List<TeamEntity> findAll();

    @Query(value = "SELECT * FROM team " +
            "LEFT JOIN team_user tu ON team.id = tu.team_id " +
            "WHERE (tu.user_id = :userId OR manager_id = :userId) " +
            "AND deleted_at IS NULL " +
            "ORDER BY name ",
            nativeQuery = true)
    List<TeamEntity> findAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "UPDATE team " +
            "SET deleted_at = now() " +
            "WHERE id = :teamId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("teamId") UUID teamId);

}
