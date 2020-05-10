package com.itechart.ema.service.impl;

import com.itechart.ema.entity.TeamEntity;
import com.itechart.ema.entity.TeamUserEntity;
import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.repository.TeamUserRepository;
import com.itechart.ema.service.TeamService;
import com.itechart.ema.service.TeamUserService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestTeamUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.itechart.ema.mapper.TeamUserMapper.TEAM_USER_MAPPER;

@Service
@AllArgsConstructor
public class TeamUserServiceImpl implements TeamUserService {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamUserRepository teamUserRepository;

    @Override
    @Transactional
    public RestTeamUser addUserToTeam(final UUID teamId, final UUID userId) {
        teamService.teamExistsOrException(teamId);
        userService.userExistsOrException(userId);
        var teamUser = TeamUserEntity.builder()
                .team(TeamEntity.builder().id(teamId).build())
                .user(UserEntity.builder().id(userId).build())
                .build();
        var saved = teamUserRepository.saveAndFlush(teamUser);
        return TEAM_USER_MAPPER.toRestTeamUser(saved);
    }

    @Override
    @Transactional
    public void removeUserFromTeam(final UUID teamId, final UUID userId) {
        teamService.teamExistsOrException(teamId);
        userService.userExistsOrException(userId);
        teamUserRepository.deleteOneByTeamIdAndUserId(teamId, userId);
    }

    @Override
    @Transactional
    public void deleteAllByTeamId(final UUID teamId) {
        teamUserRepository.deleteAllByTeamId(teamId);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(final UUID userId) {
        teamUserRepository.deleteAllByUserId(userId);
    }

}
