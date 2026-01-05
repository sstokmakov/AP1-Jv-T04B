package com.inocencl.domain.auth;

import com.inocencl.domain.model.User;
import com.inocencl.domain.service.UserService;
import com.inocencl.web.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public boolean register(SignUpRequest request) {
        if (!isCorrectSignUpRequest(request)) throw new IllegalArgumentException("Login and password must be provided");
        if (userService.isUserExist(request.username())) return false;

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(UUID.randomUUID(), request.username(), encodedPassword);
        userService.save(user);
        return true;
    }

    public UUID authorize(String header) {
        String base64 = header.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
        String[] parts = decoded.split(":", 2);
        String login = parts[0];
        User user = userService.findByLogin(login);
        return user.getUuid();
    }

    private boolean isCorrectSignUpRequest(SignUpRequest request) {
        return !(request == null || request.username().isBlank() || request.password().isBlank());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        return AppUserDetails.builder()
                .uuid(user.getUuid())
                .username(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
