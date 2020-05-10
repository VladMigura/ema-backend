package com.itechart.ema.service;

import com.itechart.generated.model.RestTask;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    RestTask getTaskById(UUID taskId);

    List<RestTask> getAllTasks();

    List<RestTask> getUserTasks(UUID userId);

    List<RestTask> getProjectTasks(UUID projectId);

    RestTask createTask(RestTask task);

    RestTask updateTask(RestTask task, UUID taskId);

    void deleteTask(UUID taskId);

}
