package com.itechart.ema.service;

import com.itechart.generated.model.RestTeam;
import com.itechart.generated.model.RestTeamUser;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    RestTeam getTeamById(UUID teamId);

    List<RestTeam> getAllTeams();

    List<RestTeam> getUserTeams(UUID userId);

    RestTeam createTeam(RestTeam team);

    RestTeam updateTeam(RestTeam team, UUID teamId);

    void deleteTeam(UUID teamId);

    RestTeamUser addUserToTeam(UUID teamId, UUID userId);

    void removeUserFromTeam(UUID teamId, UUID userId);

}
