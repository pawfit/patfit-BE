package com.peauty.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        Long accessTokenExpiration,
        Long refreshTokenExpiration,
        String accessHeader,
        String grantType
) {}