package com.itechart.ema.service.impl;

import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.ProjectService;
import com.itechart.ema.service.TeamService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.*;
import static com.itechart.ema.mapper.UserMapper.USER_MAPPER;
import static com.itechart.ema.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final TeamService teamService;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(final UUID userId) {
        return userRepository.existsById(userId);
    }

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
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public RestUser updateCurrentUser(final RestUser user) {
        var userId = getUserId();
        var existing = userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
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
        if (!teamService.existsById(teamId)) {
            throw new NotFoundException(TEAM_NOT_FOUND);
        }
        return userRepository.findAllByTeamId(teamId)
                .stream()
                .map(USER_MAPPER::toRestUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestUser> getProjectUsers(final UUID projectId) {
        if (!projectService.existsById(projectId)) {
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }
        return userRepository.findAllByProjectId(projectId)
                .stream()
                .map(USER_MAPPER::toRestUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(final UUID userId) {
        if (!existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        userRepository.softDeleteOneById(userId);
    }

}
