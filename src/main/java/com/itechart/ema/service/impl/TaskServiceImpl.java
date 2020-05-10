package com.itechart.ema.service.impl;

import com.itechart.ema.service.TaskService;
import com.itechart.generated.model.RestTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Override
    @Transactional(readOnly = true)
    public RestTask getTaskById(final UUID taskId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getAllTasks() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getUserTasks(final UUID userId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestTask> getProjectTasks(final UUID projectId) {
        return null;
    }

    @Override
    @Transactional
    public RestTask createTask(final RestTask task) {
        return null;
    }

    @Override
    @Transactional
    public RestTask updateTask(final RestTask task, final UUID taskId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTask(final UUID taskId) {

    }

}
