package com.itechart.ema.api;

import com.itechart.ema.service.UserService;
import com.itechart.generated.api.UsersApi;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class UsersApiController implements UsersApi {

    public static final String GET_CURRENT_USER_PATH = "/api/v1/users/current";

    private final UserService userService;

    @Override
    public ResponseEntity<RestUser> getCurrentUser() {
        var user = userService.getCurrentUser();
        return new ResponseEntity<>(user, OK);
    }

}
