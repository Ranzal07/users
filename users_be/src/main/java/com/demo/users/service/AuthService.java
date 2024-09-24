package com.demo.users.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.demo.users.model.*;
import com.demo.users.repository.UserRepository;
import com.demo.users.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public void authenticate(AuthRequest request, HttpServletResponse response) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        jwtUtil.generateCookie(user, response);
    }

    public Boolean isTokenValidFromCookie(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return jwtUtil.isTokenValidFromCookie(token, user);
    }

    public void refreshToken(String refreshToken, HttpServletResponse response) {
        String username = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        if (jwtUtil.isTokenValidFromCookie(refreshToken, user)) {
            jwtUtil.generateCookie(user, response);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public User getAuthUser(String token) {
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username).orElse(null);
    }

    public void clearTokenCookies(HttpServletResponse response) {
        jwtUtil.clearTokenCookies(response);
    }
}
