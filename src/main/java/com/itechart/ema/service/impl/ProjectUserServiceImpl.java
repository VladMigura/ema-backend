package com.itechart.ema.service.impl;

import com.itechart.ema.entity.ProjectEntity;
import com.itechart.ema.entity.ProjectUserEntity;
import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.repository.ProjectUserRepository;
import com.itechart.ema.service.ProjectService;
import com.itechart.ema.service.ProjectUserService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestProjectUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.itechart.ema.mapper.ProjectUserMapper.PROJECT_USER_MAPPER;

@Service
@AllArgsConstructor
public class ProjectUserServiceImpl implements ProjectUserService {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectUserRepository projectUserRepository;

    @Override
    @Transactional
    public RestProjectUser addUserToProject(final UUID projectId, final UUID userId) {
        projectService.projectExistsOrException(projectId);
        userService.userExistsOrException(userId);
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
        projectService.projectExistsOrException(projectId);
        userService.userExistsOrException(userId);
        projectUserRepository.deleteOneByProjectIdAndUserId(projectId, userId);
    }

    @Override
    @Transactional
    public void deleteAllByProjectId(final UUID projectId) {
        projectUserRepository.deleteAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(final UUID userId) {
        projectUserRepository.deleteAllByUserId(userId);
    }

}
