package com.itechart.ema.service;

import com.itechart.generated.model.*;

public interface AuthService {

    RestUser signUp(RestSignUpRequest signUpRequest);

    RestToken login(RestLoginRequest loginRequest);

    RestToken refreshToken(RestToken token);

    void changePassword(RestChangePasswordRequest changePasswordRequest);

}
