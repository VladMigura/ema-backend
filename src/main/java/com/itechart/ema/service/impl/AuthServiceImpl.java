package com.itechart.ema.service.impl;

import com.itechart.ema.exception.BadRequestException;
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

import static com.itechart.ema.exception.Constants.*;
import static com.itechart.ema.mapper.UserMapper.USER_MAPPER;
import static com.itechart.ema.util.SecurityUtil.getUserId;

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
        var existing = userRepository.findOneByEmail(email);
        if (existing.isPresent()) {
            throw new ConflictException(USER_EMAIL_CONFLICT);
        }
        var user = USER_MAPPER.toUserEntity(signUpRequest);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        return USER_MAPPER.toRestUser(userRepository.saveAndFlush(user));
    }

    @Override
    @Transactional(readOnly = true)
    public RestToken login(final RestLoginRequest loginRequest) {
        var existing = userRepository.findOneByEmail(loginRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new NotFoundException(USER_EMAIL_NOT_FOUND));
        if (!passwordEncoder.matches(loginRequest.getPassword(), existing.getPasswordHash())) {
            throw new BadRequestException(EMAIL_OR_PASSWORD_BAD_REQUEST);
        }
        return tokenProvider.createToken(existing);
    }

    @Override
    public RestToken refreshToken(final RestToken token) {
        if (!tokenValidator.validateToken(token.getRefreshToken())) {
            throw new UnauthorizedException(REFRESH_TOKEN_UNAUTHORIZED);
        }
        return tokenProvider.refreshToken(token.getRefreshToken());
    }

    @Override
    @Transactional
    public void changePassword(final RestChangePasswordRequest changePasswordRequest) {
        var userId = getUserId();
        var user = userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPasswordHash())) {
            throw new BadRequestException(CURRENT_PASSWORD_BAD_REQUEST);
        }
        user.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.saveAndFlush(user);
    }

}
