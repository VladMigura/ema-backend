package com.itechart.ema.service.impl;

import com.itechart.ema.exception.ConflictException;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.exception.UnauthorizedException;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.security.TokenProvider;
import com.itechart.ema.security.TokenValidator;
import com.itechart.ema.service.AuthService;
import com.itechart.generated.model.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itechart.ema.mapper.UserMapper.USER_MAPPER;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;

    @Override
    @Transactional
    public RestUser signUp(final RestSignUpRequest signUpRequest) {
        var email = signUpRequest.getEmail().toLowerCase();
        var existing = userRepository.finOneByEmail(email);
        if (existing.isPresent()) {
            throw new ConflictException("User with such email already exists.");
        }
        var user = USER_MAPPER.toUserEntity(signUpRequest);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        return USER_MAPPER.toRestUser(userRepository.saveAndFlush(user));
    }

    @Override
    @Transactional(readOnly = true)
    public RestToken login(final RestLoginRequest loginRequest) {
        var existing = userRepository.finOneByEmail(loginRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new NotFoundException("User with such email not found."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), existing.getPasswordHash())) {
            throw new UnauthorizedException("Incorrect email or password.");
        }
        return tokenProvider.createToken(existing);
    }

    @Override
    public RestToken refreshToken(final RestToken token) {
        if (!tokenValidator.validateToken(token.getRefreshToken())) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        return tokenProvider.refreshToken(token.getRefreshToken());
    }

    @Override
    @Transactional
    public void changePassword(final RestChangePasswordRequest changePasswordRequest) {

    }

}
