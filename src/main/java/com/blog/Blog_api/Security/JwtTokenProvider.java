package com.blog.Blog_api.Security;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.blog.Blog_api.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {

    // Existing JWT token generation method
    public static String generateJwtToken(User user) {
        // Implement JWT token generation using a library like JJWT
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000)) // 24 hours
                .signWith(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    // New method to generate a refresh token
    public static String generateRefreshToken(User user) {
        // Generate a random UUID for the refresh token
        String refreshToken = UUID.randomUUID().toString();

        // Store the refresh token securely (e.g., in a database or Redis)
        // For demonstration purposes, we'll store it in a simple map
        refreshTokens.put(user.getEmail(), refreshToken);

        return refreshToken;
    }

    // New method to validate and refresh the JWT token
    public static String refreshJwtToken(String refreshToken, User user) {
        // Validate the refresh token
        if (!refreshTokens.get(user.getEmail()).equals(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Generate a new JWT token
        String newJwtToken = generateJwtToken(user);

        return newJwtToken;
    }

    // Simple map to store refresh tokens (replace with a secure storage mechanism)
    private static Map<String, String> refreshTokens = new ConcurrentHashMap<>();
}