package com.example.insemlab.TokenConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Value("${app.request-token}")
    private String validToken;

    public boolean isValid(String token) {
        return validToken != null
                && validToken.equals(token);
    }
}