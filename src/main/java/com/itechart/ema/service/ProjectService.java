package com.itechart.ema.service;

import com.itechart.generated.model.RestProject;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    boolean existsById(UUID projectId);

    void projectExistsOrException(UUID projectId);

    RestProject getProjectById(UUID projectId);

    List<RestProject> getAllProjects();

    List<RestProject> getUserProjects(UUID userId);

    RestProject createProject(RestProject project);

    RestProject updateProject(RestProject project, UUID projectId);

    void deleteProject(UUID projectId);

}
