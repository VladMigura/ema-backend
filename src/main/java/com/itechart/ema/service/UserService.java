package com.itechart.ema.service;

import com.itechart.generated.model.RestUser;

import java.util.List;

public interface UserService {

    RestUser getCurrentUser();

    RestUser updateCurrentUser(RestUser user);

    List<RestUser> getAllUsers();

}
