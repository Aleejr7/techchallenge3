package com.appserviceagendamento.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secret;

    public Claims extractClaims(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    public Long getUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.get("userId", Long.class);
    }
}

