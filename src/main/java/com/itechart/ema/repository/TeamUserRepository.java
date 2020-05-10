package com.itechart.ema.repository;

import com.itechart.ema.entity.TeamUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUserEntity, UUID> {

    @Modifying
    @Query(value = "DELETE FROM team_user " +
            "WHERE team_id = :teamId " +
            "AND user_id = :userId ",
            nativeQuery = true)
    void deleteOneByTeamIdAndUserId(@Param("teamId") UUID teamId,
                                    @Param("userId") UUID userId);

    @Modifying
    @Query(value = "DELETE FROM team_user " +
            "WHERE team_id = :teamId ",
            nativeQuery = true)
    void deleteAllByTeamId(@Param("teamId") UUID teamId);

    @Modifying
    @Query(value = "DELETE FROM team_user " +
            "WHERE user_id = :userId ",
            nativeQuery = true)
    void deleteAllByUserId(@Param("userId") UUID userId);

}
