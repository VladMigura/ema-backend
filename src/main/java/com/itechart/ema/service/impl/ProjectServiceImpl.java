package com.itechart.ema.service.impl;

import com.itechart.ema.entity.ProjectEntity;
import com.itechart.ema.entity.ProjectUserEntity;
import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.BadRequestException;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.ProjectRepository;
import com.itechart.ema.repository.ProjectUserRepository;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.ProjectService;
import com.itechart.generated.model.RestProject;
import com.itechart.generated.model.RestProjectUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.entity.enums.UserRoleEntity.MANAGER;
import static com.itechart.ema.entity.enums.UserRoleEntity.SCRUM_MASTER;
import static com.itechart.ema.exception.Constants.*;
import static com.itechart.ema.mapper.ProjectMapper.PROJECT_MAPPER;
import static com.itechart.ema.mapper.ProjectUserMapper.PROJECT_USER_MAPPER;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;


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
        userExistsOrException(userId);
        return projectRepository.findAllByUserId(userId)
                .stream()
                .map(PROJECT_MAPPER::toRestProject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestProject createProject(final RestProject project) {
        validateProjectUserRoles(project);
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
        validateProjectUserRoles(project);
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
        projectUserRepository.deleteAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public RestProjectUser addUserToProject(final UUID projectId, final UUID userId) {
        projectExistsOrException(projectId);
        userExistsOrException(userId);
        var projectUser = ProjectUserEntity.builder()
                .project(ProjectEntity.builder().id(projectId).build())
                .user(UserEntity.builder().id(userId).build())
                .build();
        var saved = projectUserRepository.saveAndFlush(projectUser);
        return PROJECT_USER_MAPPER.toRestProjectUser(saved);
    }

    @Override
    @Transactional
    public void removeUserFromProject(final UUID projectId, final UUID userId) {
        projectExistsOrException(projectId);
        userExistsOrException(userId);
        projectUserRepository.deleteOneByProjectIdAndUserId(projectId, userId);
    }

    private void projectExistsOrException(final UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
    }

    private void userExistsOrException(final UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    private void validateProjectUserRoles(final RestProject project) {
        var manager = userRepository.findOneById(project.getManager().getId())
                .orElseThrow(() -> new NotFoundException(MANAGER_NOT_FOUND));
        var scrumMaster = userRepository.findOneById(project.getScrumMaster().getId())
                .orElseThrow(() -> new NotFoundException(SCRUM_MASTER_NOT_FOUND));
        if (!MANAGER.equals(manager.getRole())) {
            throw new BadRequestException(INCORRECT_MANAGER_ROLE);
        }
        if (!SCRUM_MASTER.equals(scrumMaster.getRole())) {
            throw new BadRequestException(INCORRECT_SCRUM_MASTER_ROLE);
        }
    }

}
