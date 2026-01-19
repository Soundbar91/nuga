package com.soundbar91.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        long accessTokenExpiration,
        long refreshTokenExpiration,
        String issuer
) {
    public JwtProperties {
        if (secret == null || secret.isBlank()) {
            secret = "default-secret-key-for-development-only-change-in-production";
        }
        if (accessTokenExpiration <= 0) {
            accessTokenExpiration = 3600000L; // 1시간
        }
        if (refreshTokenExpiration <= 0) {
            refreshTokenExpiration = 604800000L; // 7일
        }
        if (issuer == null || issuer.isBlank()) {
            issuer = "nuga";
        }
    }
}
