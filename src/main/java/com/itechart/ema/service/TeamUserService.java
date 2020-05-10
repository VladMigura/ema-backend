package com.itechart.ema.service;

import com.itechart.generated.model.RestTeamUser;

import java.util.UUID;

public interface TeamUserService {

    RestTeamUser addUserToTeam(UUID teamId, UUID userId);

    void removeUserFromTeam(UUID teamId, UUID userId);

    void deleteAllByTeamId(UUID teamId);

    void deleteAllByUserId(UUID userId);

}
