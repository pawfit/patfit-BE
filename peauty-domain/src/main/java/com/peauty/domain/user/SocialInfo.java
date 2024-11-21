package com.peauty.domain.user;

public record SocialInfo(
        String socialId,
        SocialPlatform socialPlatform,
        String nickname,
        String pictureImageUrl
) {
}
