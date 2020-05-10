package com.itechart.ema.api;

import com.itechart.ema.service.TaskService;
import com.itechart.generated.api.TasksApi;
import com.itechart.generated.model.RestTask;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
public class TasksApiController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<RestTask> createTask(@Valid @RequestBody final RestTask body) {
        var task = taskService.createTask(body);
        return new ResponseEntity<>(task, CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") final UUID taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<RestTask>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, OK);
    }

    @Override
    public ResponseEntity<RestTask> getTaskById(@PathVariable("taskId") final UUID taskId) {
        var task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, OK);
    }

    @Override
    public ResponseEntity<RestTask> updateTask(@Valid @RequestBody RestTask body,
                                               @PathVariable("taskId") final UUID taskId) {
        var task = taskService.updateTask(body, taskId);
        return new ResponseEntity<>(task, OK);
    }

}
