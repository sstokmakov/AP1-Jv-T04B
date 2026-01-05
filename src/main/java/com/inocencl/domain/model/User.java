package com.inocencl.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    protected User() {
    }

    public User(UUID uuid, String login, String password) {
        this.uuid = uuid;
        this.login = login;
        this.password = password;
    }
}
