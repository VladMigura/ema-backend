package com.itechart.ema.service.impl;

import com.itechart.ema.service.TeamService;
import com.itechart.generated.model.RestTeam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    @Override
    @Transactional(readOnly = true)
    public RestTeam getTeamById(final UUID teamId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTeam> getAllTeams() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTeam> getUserTeams(final UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public RestTeam createTeam(final RestTeam team) {
        return null;
    }

    @Override
    @Transactional
    public RestTeam updateTeam(final RestTeam team, final UUID teamId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTeam(final UUID teamId) {

    }

}
