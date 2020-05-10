package com.itechart.ema.service.impl;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.TaskRepository;
import com.itechart.ema.service.ProjectService;
import com.itechart.ema.service.TaskService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.*;
import static com.itechart.ema.mapper.TaskMapper.TASK_MAPPER;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(final UUID taskId) {
        return taskRepository.existsById(taskId);
    }

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
        if (!userService.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return taskRepository.findAllByDevOwnerId(userId)
                .stream()
                .map(TASK_MAPPER::toRestTask)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getProjectTasks(final UUID projectId) {
        if (!projectService.existsById(projectId)) {
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
        return taskRepository.findAllByProjectId(projectId)
                .stream()
                .map(TASK_MAPPER::toRestTask)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestTask createTask(final RestTask task) {
        var entity = TASK_MAPPER.toTaskEntity(task);
        entity.setDevOwner(UserEntity.builder().id(task.getDevOwner().getId()).build());
        entity.setCreatedBy(UserEntity.builder().id(task.getCreatedBy().getId()).build());
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
        if (!existsById(taskId)) {
            throw new NotFoundException(TASK_NOT_FOUND);
        }
        taskRepository.softDeleteOneById(taskId);
    }

}
