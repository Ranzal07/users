package com.demo.users.controller;

import com.demo.users.payload.AuthUserResponse;
import com.demo.users.service.*;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final AuthService authService;

    @GetMapping("/authUser")
    public ResponseEntity<?> getAuthUser(@CookieValue String accessToken) {
        try {
            AuthUserResponse authUserResponse = authService.getAuthUser(accessToken);
            return ResponseEntity.ok(authUserResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get auth user: " + e.getMessage());
        }
    }
}
