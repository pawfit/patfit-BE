package com.peauty.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth")
public record OAuthProperties(
        String kakaoPublicKeyInfo,
        String kakaoClientId,
        String kakaoRedirectUrl,
        String applePublicKeyUrl,
        String appleClientId,
        String appleRedirectUrl,
        String googleClientId,
        String googleClientSecret,
        String googleRedirectUrl
) {}
