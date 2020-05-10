package com.itechart.ema.repository;

import com.itechart.ema.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM ema_user " +
            "WHERE id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserEntity> findOneById(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM ema_user " +
            "WHERE email = :email " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserEntity> findOneByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM ema_user " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<UserEntity> findAll();

    @Modifying
    @Query(value = "UPDATE ema_user " +
            "SET deleted_at = now() " +
            "WHERE id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("userId") UUID userId);

}
