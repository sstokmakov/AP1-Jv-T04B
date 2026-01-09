package com.tokmakov.datasource.user;

import com.tokmakov.domain.model.User;

public class UserEntityMapper {
    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUuid(user.getUuid());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        return entity;
    }

    public static User toUser(UserEntity entity) {
        return new User(entity.getUuid(),
                entity.getLogin(),
                entity.getPassword());
    }

}
