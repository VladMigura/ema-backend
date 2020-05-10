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

    @Query(value = "SELECT * FROM team " +
            "WHERE id = :teamId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<TeamEntity> findOneById(@Param("teamId") UUID teamId);

    @Query(value = "SELECT * FROM team " +
            "WHERE deleted_at IS NULL ",
            nativeQuery = true)
    List<TeamEntity> findAll();

    @Modifying
    @Query(value = "UPDATE team " +
            "SET deleted_at = now() " +
            "WHERE id = :teamId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("teamId") UUID teamId);

}
