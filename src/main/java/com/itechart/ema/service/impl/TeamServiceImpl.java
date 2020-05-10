package com.itechart.ema.service.impl;

import com.itechart.ema.entity.TeamEntity;
import com.itechart.ema.entity.TeamUserEntity;
import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.TeamRepository;
import com.itechart.ema.repository.TeamUserRepository;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.TeamService;
import com.itechart.generated.model.RestTeam;
import com.itechart.generated.model.RestTeamUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.TEAM_NOT_FOUND;
import static com.itechart.ema.exception.Constants.USER_NOT_FOUND;
import static com.itechart.ema.mapper.TeamMapper.TEAM_MAPPER;
import static com.itechart.ema.mapper.TeamUserMapper.TEAM_USER_MAPPER;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    @Override
    @Transactional(readOnly = true)
    public RestTeam getTeamById(final UUID teamId) {
        return teamRepository.findOneById(teamId)
                .map(TEAM_MAPPER::toRestTeam)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTeam> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TEAM_MAPPER::toRestTeam)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTeam> getUserTeams(final UUID userId) {
        userExistsOrException(userId);
        return teamRepository.findAllByUserId(userId)
                .stream()
                .map(TEAM_MAPPER::toRestTeam)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestTeam createTeam(final RestTeam team) {
        var entity = TEAM_MAPPER.toTeamEntity(team);
        entity.setManager(UserEntity.builder().id(team.getManager().getId()).build());
        var saved = teamRepository.saveAndFlush(entity);
        return TEAM_MAPPER.toRestTeam(saved);
    }

    @Override
    @Transactional
    public RestTeam updateTeam(final RestTeam team, final UUID teamId) {
        var existing = teamRepository.findOneById(teamId)
                .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));
        var updated = TEAM_MAPPER.updateEntity(team, existing);
        updated.setManager(UserEntity.builder().id(team.getManager().getId()).build());
        return TEAM_MAPPER.toRestTeam(teamRepository.saveAndFlush(updated));
    }

    @Override
    @Transactional
    public void deleteTeam(final UUID teamId) {
        teamExistsOrException(teamId);
        teamRepository.softDeleteOneById(teamId);
        teamUserRepository.deleteAllByTeamId(teamId);
    }

    @Override
    @Transactional
    public RestTeamUser addUserToTeam(final UUID teamId, final UUID userId) {
        teamExistsOrException(teamId);
        userExistsOrException(userId);
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
        teamExistsOrException(teamId);
        userExistsOrException(userId);
        teamUserRepository.deleteOneByTeamIdAndUserId(teamId, userId);
    }

    private void teamExistsOrException(final UUID teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }
    }

    private void userExistsOrException(final UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

}
