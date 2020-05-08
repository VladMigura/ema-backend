package com.itechart.ema.service.impl;

import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.UserRepository;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.findOneById(userId)
                .map(USER_MAPPER::toRestUser)
                .orElseThrow(() -> new NotFoundException("Could not get the current user."));
    }

}
