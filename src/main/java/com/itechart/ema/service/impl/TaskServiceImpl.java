package com.itechart.ema.service.impl;

import com.itechart.ema.entity.ProjectEntity;
import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.ProjectRepository;
import com.itechart.ema.repository.TaskRepository;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.TaskService;
import com.itechart.generated.model.RestTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.*;
import static com.itechart.ema.mapper.TaskMapper.TASK_MAPPER;
import static com.itechart.ema.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    @Override
    @Transactional(readOnly = true)
    public RestTask getTaskById(final UUID taskId) {
        return taskRepository.findOneById(taskId)
                .map(TASK_MAPPER::toRestTask)
                .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TASK_MAPPER::toRestTask)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getUserTasks(final UUID userId) {
        userExistsOrException(userId);
        return taskRepository.findAllByDevOwnerId(userId)
                .stream()
                .map(TASK_MAPPER::toRestTask)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getProjectTasks(final UUID projectId) {
        projectExistsOrException(projectId);
        return taskRepository.findAllByProjectId(projectId)
                .stream()
                .map(TASK_MAPPER::toRestTask)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestTask createTask(final RestTask task) {
        var userId = getUserId();
        var entity = TASK_MAPPER.toTaskEntity(task);
        entity.setProject(ProjectEntity.builder().id(task.getProject().getId()).build());
        entity.setDevOwner(UserEntity.builder().id(task.getDevOwner().getId()).build());
        entity.setCreatedBy(UserEntity.builder().id(userId).build());
        var saved = taskRepository.saveAndFlush(entity);
        return TASK_MAPPER.toRestTask(saved);
    }

    @Override
    @Transactional
    public RestTask updateTask(final RestTask task, final UUID taskId) {
        var existing = taskRepository.findOneById(taskId)
                .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
        var updated = TASK_MAPPER.updateEntity(task, existing);
        updated.setDevOwner(UserEntity.builder().id(task.getDevOwner().getId()).build());
        return TASK_MAPPER.toRestTask(taskRepository.saveAndFlush(updated));
    }

    @Override
    @Transactional
    public void deleteTask(final UUID taskId) {
        taskExistsOrException(taskId);
        taskRepository.softDeleteOneById(taskId);
    }

    private void taskExistsOrException(final UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException(TASK_NOT_FOUND);
        }
    }

    private void userExistsOrException(final UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    private void projectExistsOrException(final UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
    }

}
