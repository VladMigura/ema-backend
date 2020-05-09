package com.itechart.ema.api;

import com.itechart.ema.service.UserService;
import com.itechart.generated.api.UsersApi;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class UsersApiController implements UsersApi {

    public static final String GET_CURRENT_USER_PATH = "/api/v1/users/current";
    public static final String UPDATE_CURRENT_USER_PATH = "/api/v1/users/current";
    public static final String GET_ALL_USERS_PATH = "/api/v1/users";

    private final UserService userService;

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
    public ResponseEntity<List<RestUser>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, OK);
    }

}
