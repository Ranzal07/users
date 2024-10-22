package com.demo.users.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.demo.users.model.*;
import com.demo.users.payload.AuthRequest;
import com.demo.users.payload.AuthUserResponse;
import com.demo.users.payload.SignupRequest;
import com.demo.users.repository.*;
import com.demo.users.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final EmploymentRepository employmentRepository;
    private final LevelRepository levelRepository;
    private final PositionRepository positionRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request, HttpServletResponse response) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadCredentialsException("Username is already taken.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException("Email is already registered.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadCredentialsException("Passwords do not match.");
        }

        Level level = levelRepository.findById(1L).orElseThrow(() -> new RuntimeException("Level not found"));
        Position position = positionRepository.findById(2L).orElseThrow(() -> new RuntimeException("Position not found"));

        Employment employment = Employment.builder()
                .level(level)
                .position(position)
                .status("Employed")
                .build();
        employmentRepository.save(employment);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .employment(employment)
                .build();

        userRepository.save(user);
        jwtUtil.generateCookie(user, response);
    }  

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

    public AuthUserResponse getAuthUser(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username).orElse(null);
        AuthUserResponse authUserResponse = AuthUserResponse.fromUser(user);
        return authUserResponse;
    }

    public void clearTokenCookies(HttpServletResponse response) {
        jwtUtil.clearTokenCookies(response);
    }
}
