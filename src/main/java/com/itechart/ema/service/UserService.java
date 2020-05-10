package com.itechart.ema.service;

import com.itechart.generated.model.RestUser;

import java.util.List;
import java.util.UUID;

public interface UserService {

    boolean existsById(UUID userId);

    RestUser getCurrentUser();

    RestUser getUserById(UUID userId);

    RestUser updateCurrentUser(RestUser user);

    List<RestUser> getAllUsers();

    List<RestUser> getTeamUsers(UUID teamId);

    List<RestUser> getProjectUsers(UUID projectId);

    void deleteUser(UUID userId);

}
