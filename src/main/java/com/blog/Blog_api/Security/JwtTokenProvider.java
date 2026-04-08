package com.blog.Blog_api.Security;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.blog.Blog_api.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {

    // Existing JWT token generation method
    public static String generateJwtToken(Authentication auth) {
        // Implement JWT token generation using a library like JJWT
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles=populateAuthorities(authorities);
        return Jwts.builder()
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }
    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority()).append(",");
        }
        if (roles.length() > 0) {
            roles.deleteCharAt(roles.length() - 1); // Remove trailing comma
        }
        return roles.toString();
    }
}