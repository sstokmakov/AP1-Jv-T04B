package com.tokmakov.web.model;

import java.util.UUID;

public record UserDto(UUID uuid, String login) {
}
