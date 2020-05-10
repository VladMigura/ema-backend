package com.itechart.ema.service.impl;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.ProjectRepository;
import com.itechart.ema.service.ProjectService;
import com.itechart.ema.service.ProjectUserService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestProject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.PROJECT_NOT_FOUND;
import static com.itechart.ema.mapper.ProjectMapper.PROJECT_MAPPER;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectUserService projectUserService;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(final UUID projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public void projectExistsOrException(final UUID projectId) {
        if (!existsById(projectId)) {
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RestProject getProjectById(final UUID projectId) {
        return projectRepository.findOneById(projectId)
                .map(PROJECT_MAPPER::toRestProject)
                .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestProject> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(PROJECT_MAPPER::toRestProject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestProject> getUserProjects(final UUID userId) {
        userService.userExistsOrException(userId);
        return projectRepository.findAllByUserId(userId)
                .stream()
                .map(PROJECT_MAPPER::toRestProject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestProject createProject(final RestProject project) {
        var entity = PROJECT_MAPPER.toProjectEntity(project);
        entity.setManager(UserEntity.builder().id(project.getManager().getId()).build());
        entity.setScrumMaster(UserEntity.builder().id(project.getScrumMaster().getId()).build());
        var saved = projectRepository.saveAndFlush(entity);
        return PROJECT_MAPPER.toRestProject(saved);
    }

    @Override
    @Transactional
    public RestProject updateProject(final RestProject project, final UUID projectId) {
        var existing = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
        var updated = PROJECT_MAPPER.updateEntity(project, existing);
        updated.setManager(UserEntity.builder().id(project.getManager().getId()).build());
        updated.setScrumMaster(UserEntity.builder().id(project.getScrumMaster().getId()).build());
        return PROJECT_MAPPER.toRestProject(projectRepository.saveAndFlush(updated));
    }

    @Override
    @Transactional
    public void deleteProject(final UUID projectId) {
        projectExistsOrException(projectId);
        projectRepository.softDeleteOneById(projectId);
        projectUserService.deleteAllByProjectId(projectId);
    }

}
