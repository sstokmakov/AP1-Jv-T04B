package com.inocencl.domain.service;

import com.inocencl.domain.model.User;

import java.util.UUID;

public interface UserService {
    User findByLogin(String login);

    User findByUuid(UUID uuid);

    User save(User user);

    boolean isUserExist(String login);
}
