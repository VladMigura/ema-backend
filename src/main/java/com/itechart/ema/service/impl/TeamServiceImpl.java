package com.itechart.ema.service.impl;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.TeamRepository;
import com.itechart.ema.service.TeamService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestTeam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.TEAM_NOT_FOUND;
import static com.itechart.ema.exception.Constants.USER_NOT_FOUND;
import static com.itechart.ema.mapper.TeamMapper.TEAM_MAPPER;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final UserService userService;
    private final TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(final UUID teamId) {
        return teamRepository.existsById(teamId);
    }

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
        if (!userService.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
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
        if (!existsById(teamId)) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }
        teamRepository.softDeleteOneById(teamId);
    }

}
