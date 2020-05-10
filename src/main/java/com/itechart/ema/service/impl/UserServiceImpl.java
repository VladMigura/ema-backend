package com.itechart.ema.service.impl;

import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.mapper.UserMapper.USER_MAPPER;
import static com.itechart.ema.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public RestUser getCurrentUser() {
        var userId = getUserId();
        return getUserById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public RestUser getUserById(final UUID userId) {
        return userRepository.findOneById(userId)
                .map(USER_MAPPER::toRestUser)
                .orElseThrow(() -> new NotFoundException("Could not get the current user."));
    }

    @Override
    @Transactional
    public RestUser updateCurrentUser(final RestUser user) {
        var userId = getUserId();
        var existing = userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found."));
        var updated = USER_MAPPER.updateEntity(user, existing);
        return USER_MAPPER.toRestUser(userRepository.saveAndFlush(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestUser> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(USER_MAPPER::toRestUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestUser> getTeamUsers(final UUID teamId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestUser> getProjectUsers(final UUID projectId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(final UUID userId) {

    }

}
