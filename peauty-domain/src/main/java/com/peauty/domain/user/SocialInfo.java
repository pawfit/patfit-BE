package com.peauty.domain.user;

public record SocialInfo(
        String oidcProviderId,
        OidcProviderType oidcProviderType,
        String nickname,
        String pictureImageUrl
) {
}
