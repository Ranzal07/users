package com.demo.users.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.demo.users.model.User;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String tokenSecretKey;

    @Value("${security.jwt.access-expiration-time}")
    private long accessTokenExp;

    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshTokenExp;

    public void generateCookie(User user, HttpServletResponse response) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        Long accessTokenMaxAge = accessTokenExp / 1000;
        Long refreshTokenMaxAge = refreshTokenExp / 1000;
        setCookie(accessToken, refreshToken, accessTokenMaxAge, refreshTokenMaxAge, response);
    }

    private String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExp))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExp))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private void setCookie(String token, String refreshToken, long accessTokenMaxAge, long refreshTokenMaxAge, HttpServletResponse response) {
        ResponseCookie tokenCookie = ResponseCookie.from("accessToken", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(accessTokenMaxAge)
            .sameSite("Strict")
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshTokenMaxAge)
            .sameSite("Strict")
            .build();

        response.addHeader("Set-Cookie", tokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }
    
    public boolean isTokenValidFromCookie(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !extractExpiration(token).before(new Date()));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void clearTokenCookies(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(tokenSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
