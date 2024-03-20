package com.sachet.authserveramazonjava.service;

import com.sachet.authserveramazonjava.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String SECURE_KEY;

    public JwtService(@Value("${SECURE_KEY}") String SECURE_KEY) {
        this.SECURE_KEY = SECURE_KEY;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    public String createToken(Map<String, ?> claims, User user) {
        var key = Keys.hmacShaKeyFor(SECURE_KEY.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()+"|"+user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +  60 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Date extractExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        var keys = Keys.hmacShaKeyFor(SECURE_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(keys)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
