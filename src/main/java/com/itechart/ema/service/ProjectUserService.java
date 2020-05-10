package com.itechart.ema.service;

import com.itechart.generated.model.RestProjectUser;

import java.util.UUID;

public interface ProjectUserService {

    RestProjectUser addUserToProject(UUID projectId, UUID userId);

    void removeUserFromProject(UUID projectId, UUID userId);

    void deleteAllByProjectId(UUID projectId);

    void deleteAllByUserId(UUID userId);

}
