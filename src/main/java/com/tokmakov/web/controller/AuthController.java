package com.tokmakov.web.controller;

import com.tokmakov.domain.service.AuthService;
import com.tokmakov.web.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public boolean register(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }

    @GetMapping("/authorize")
    public UUID authorize(@RequestHeader("Authorization") String header) {
        return authService.authorize(header);
    }
}
