package com.inocencl.domain.service;

import com.inocencl.datasource.UserRepository;
import com.inocencl.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User findByUuid(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean isUserExist(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent();
    }
}
