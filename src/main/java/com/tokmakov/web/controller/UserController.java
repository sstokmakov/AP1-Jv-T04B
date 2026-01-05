package com.tokmakov.web.controller;

import com.tokmakov.domain.model.User;
import com.tokmakov.domain.service.UserService;
import com.tokmakov.web.model.UserDto;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public UserDto getUser(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid) {
        UUID parsedUuid = UUID.fromString(uuid);
        User user = userService.findByUuid(parsedUuid);
        return new UserDto(user.getUuid(), user.getLogin());
    }
}
