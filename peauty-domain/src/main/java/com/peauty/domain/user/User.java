package com.peauty.domain.user;

public record User(
        Long id,
        String oidcProviderId,
        OidcProviderType oidcProviderType,
        String name,
        String nickname,
        String phoneNum,
        String profileImageUrl,
        Status status,
        Role role
) {



}
