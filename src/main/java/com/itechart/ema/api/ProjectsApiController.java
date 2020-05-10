package com.itechart.ema.api;

import com.itechart.ema.service.ProjectService;
import com.itechart.ema.service.TaskService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.api.ProjectsApi;
import com.itechart.generated.model.RestProject;
import com.itechart.generated.model.RestTask;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
public class ProjectsApiController implements ProjectsApi {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<RestProject> createProject(@Valid @RequestBody final RestProject body) {
        var project = projectService.createProject(body);
        return new ResponseEntity<>(project, CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") final UUID projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<RestProject>> getAllProjects() {
        var projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, OK);
    }

    @Override
    public ResponseEntity<RestProject> getProjectById(@PathVariable("projectId") final UUID projectId) {
        var project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, OK);
    }

    @Override
    public ResponseEntity<List<RestTask>> getProjectTasks(@PathVariable("projectId") final UUID projectId) {
        var tasks = taskService.getProjectTasks(projectId);
        return new ResponseEntity<>(tasks, OK);
    }

    @Override
    public ResponseEntity<List<RestUser>> getProjectUsers(@PathVariable("projectId") final UUID projectId) {
        var users = userService.getProjectUsers(projectId);
        return new ResponseEntity<>(users, OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<RestProject> updateProject(@Valid @RequestBody final RestProject body,
                                                     @PathVariable("projectId") final UUID projectId) {
        var project = projectService.updateProject(body, projectId);
        return new ResponseEntity<>(project, OK);
    }

}
