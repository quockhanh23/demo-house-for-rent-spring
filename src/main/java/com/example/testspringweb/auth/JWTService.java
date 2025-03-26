package com.example.testspringweb.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
}
