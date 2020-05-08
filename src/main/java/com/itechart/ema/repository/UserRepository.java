package com.itechart.ema.repository;

import com.itechart.ema.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    Optional<UserEntity> finOneByEmail(@Param("email") String email);

}
