package com.itechart.ema.service.impl;

import com.itechart.ema.service.ProjectService;
import com.itechart.generated.model.RestProject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Override
    @Transactional(readOnly = true)
    public RestProject getProjectById(final UUID projectId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestProject> getAllProjects() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestProject> getUserProjects(final UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public RestProject createProject(final RestProject project) {
        return null;
    }

    @Override
    @Transactional
    public RestProject updateProject(final RestProject project, final UUID projectId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteProject(final UUID projectId) {

    }

}
