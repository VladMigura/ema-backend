package com.itechart.ema.api;

import com.itechart.ema.service.AuthService;
import com.itechart.generated.api.AuthApi;
import com.itechart.generated.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@AllArgsConstructor
public class AuthApiController implements AuthApi {

    public static final String SIGN_UP_PATH = "/api/v1/auth/signup";
    public static final String LOGIN_PATH = "/api/v1/auth/login";
    public static final String REFRESH_TOKEN_PATH = "/api/v1/auth/refresh-token";
    public static final String CHANGE_PASSWORD_PATH = "/api/v1/auth/change-password";

    private final AuthService authService;

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<RestUser> signUp(@Valid @RequestBody final RestSignUpRequest body) {
        var user = authService.signUp(body);
        return new ResponseEntity<>(user, CREATED);
    }

    @Override
    public ResponseEntity<RestToken> login(@Valid @RequestBody final RestLoginRequest body) {
        var token = authService.login(body);
        return new ResponseEntity<>(token, CREATED);
    }

    @Override
    public ResponseEntity<RestToken> refreshToken(@Valid @RequestBody final RestToken body) {
        var token = authService.refreshToken(body);
        return new ResponseEntity<>(token, CREATED);
    }

    @Override
    public ResponseEntity<Void> changePassword(@Valid @RequestBody final RestChangePasswordRequest body) {
        authService.changePassword(body);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
