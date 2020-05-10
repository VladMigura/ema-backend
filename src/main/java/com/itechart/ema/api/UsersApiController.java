package com.itechart.ema.api;

import com.itechart.ema.service.*;
import com.itechart.generated.api.UsersApi;
import com.itechart.generated.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class UsersApiController implements UsersApi {

    private final UserService userService;
    private final PostService postService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final TeamService teamService;

    @Override
    public ResponseEntity<RestUser> getCurrentUser() {
        var user = userService.getCurrentUser();
        return new ResponseEntity<>(user, OK);
    }

    @Override
    public ResponseEntity<RestUser> updateCurrentUser(@Valid @RequestBody final RestUser body) {
        var user = userService.updateCurrentUser(body);
        return new ResponseEntity<>(user, OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<RestUser> updateUser(@Valid @RequestBody final RestUser body,
                                               @PathVariable("userId") final UUID userId) {
        var user = userService.updateUser(body, userId);
        return new ResponseEntity<>(user, OK);
    }

    @Override
    public ResponseEntity<List<RestUser>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final UUID userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<RestUser> getUserById(@PathVariable("userId") final UUID userId) {
        var user = userService.getUserById(userId);
        return new ResponseEntity<>(user, OK);
    }

    @Override
    public ResponseEntity<List<RestPost>> getUserPosts(@PathVariable("userId") final UUID userId) {
        var posts = postService.getUserPosts(userId);
        return new ResponseEntity<>(posts, OK);
    }

    @Override
    public ResponseEntity<List<RestProject>> getUserProjects(@PathVariable("userId") final UUID userId) {
        var projects = projectService.getUserProjects(userId);
        return new ResponseEntity<>(projects, OK);
    }

    @Override
    public ResponseEntity<List<RestTask>> getUserTasks(@PathVariable("userId") final UUID userId) {
        var tasks = taskService.getUserTasks(userId);
        return new ResponseEntity<>(tasks, OK);
    }

    @Override
    public ResponseEntity<List<RestTeam>> getUserTeams(@PathVariable("userId") final UUID userId) {
        var teams = teamService.getUserTeams(userId);
        return new ResponseEntity<>(teams, OK);
    }

}
