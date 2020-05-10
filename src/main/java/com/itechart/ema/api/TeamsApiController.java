package com.itechart.ema.api;

import com.itechart.ema.service.TeamService;
import com.itechart.ema.service.TeamUserService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.api.TeamsApi;
import com.itechart.generated.model.RestTeam;
import com.itechart.generated.model.RestTeamUser;
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
public class TeamsApiController implements TeamsApi {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamUserService teamUserService;

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<RestTeam> createTeam(@Valid @RequestBody final RestTeam body) {
        var team = teamService.createTeam(body);
        return new ResponseEntity<>(team, CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGEER')")
    public ResponseEntity<Void> deleteTeam(@PathVariable("teamId") final UUID teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<RestTeam>> getAllTeams() {
        var teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, OK);
    }

    @Override
    public ResponseEntity<RestTeam> getTeamById(@PathVariable("teamId") final UUID teamId) {
        var team = teamService.getTeamById(teamId);
        return new ResponseEntity<>(team, OK);
    }

    @Override
    public ResponseEntity<List<RestUser>> getTeamUsers(@PathVariable("teamId") final UUID teamId) {
        var users = userService.getTeamUsers(teamId);
        return new ResponseEntity<>(users, OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<RestTeam> updateTeam(@Valid @RequestBody final RestTeam body,
                                               @PathVariable("teamId") final UUID teamId) {
        var team = teamService.updateTeam(body, teamId);
        return new ResponseEntity<>(team, OK);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<RestTeamUser> addUserToTeam(@PathVariable("teamId") final UUID teamId,
                                                      @PathVariable("userId") final UUID userId) {
        var teamUser = teamUserService.addUserToTeam(teamId, userId);
        return new ResponseEntity<>(teamUser, CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> removeUserFromTeam(@PathVariable("teamId") final UUID teamId,
                                                   @PathVariable("userId") final UUID userId) {
        teamUserService.removeUserFromTeam(teamId, userId);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
