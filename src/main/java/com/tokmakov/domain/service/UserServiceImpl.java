package com.tokmakov.domain.service;

import com.tokmakov.datasource.user.UserEntity;
import com.tokmakov.datasource.user.UserEntityMapper;
import com.tokmakov.datasource.user.UserRepository;
import com.tokmakov.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User findByLogin(String login) {
        UserEntity entity = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserEntityMapper.toUser(entity);
    }

    public User findByUuid(UUID uuid) {
        UserEntity entity = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserEntityMapper.toUser(entity);
    }

    public User save(User user) {
        UserEntity entity = userRepository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.toUser(entity);
    }

    @Override
    public boolean isUserExist(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        return user.isPresent();
    }
}
