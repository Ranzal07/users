package com.demo.users.controller;

import com.demo.users.payload.AuthRequest;
import com.demo.users.payload.SignupRequest;
import com.demo.users.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request, HttpServletResponse response) {
        try {
            authService.signup(request, response);
            return ResponseEntity.ok("Signup successful");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during signup: " + e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            authService.authenticate(request, response);
            return ResponseEntity.ok("Authentication successful");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication: " + e.getMessage());
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshToken(@CookieValue(required = false) String accessToken, @CookieValue(required = false) String refreshToken, HttpServletResponse response) {
        if (accessToken == null || accessToken.isEmpty()) {
            authService.clearTokenCookies(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
        }
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token provided");
        }
        if (authService.isTokenValidFromCookie(accessToken)) {
            try {
                authService.refreshToken(refreshToken, response);
                return ResponseEntity.ok("Tokens refreshed successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            authService.clearTokenCookies(response);
            return ResponseEntity.ok("Successfully logged out");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No authenticated session found");
    }
}
