 package com.yourpackage.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private String bearerToken;

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
