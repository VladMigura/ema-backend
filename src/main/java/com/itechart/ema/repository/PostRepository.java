package com.itechart.ema.repository;

import com.itechart.ema.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    @Query(value = "SELECT * FROM post " +
            "WHERE id = :postId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<PostEntity> findOneById(@Param("postId") UUID postId);

    @Query(value = "SELECT * FROM post " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at DESC ",
            nativeQuery = true)
    List<PostEntity> findAll();

    @Modifying
    @Query(value = "UPDATE post " +
            "SET deleted_at = now() " +
            "WHERE id = :postId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    void softDeleteOneById(@Param("postId") UUID postId);

}
