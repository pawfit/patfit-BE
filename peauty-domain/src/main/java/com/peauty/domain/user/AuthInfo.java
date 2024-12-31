package com.peauty.domain.user;

public record AuthInfo(
        Long userId,
        String socialId,
        SocialPlatform socialPlatform,
        Status status,
        Role role
) {
}
