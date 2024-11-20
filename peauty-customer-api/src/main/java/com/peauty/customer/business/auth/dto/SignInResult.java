package com.peauty.customer.business.auth.dto;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;
import com.peauty.domain.user.OidcProviderType;

public record SignInResult(
        String accessToken,
        String refreshToken,
        String oidcProviderId,
        OidcProviderType oidcProviderType,
        String nickname,
        String profileImageUrl
) {

    public static SignInResult from(SignTokens signTokens, User user) {
        return new SignInResult(
                signTokens.accessToken(),
                signTokens.refreshToken(),
                user.oidcProviderId(),
                user.oidcProviderType(),
                user.nickname(),
                user.profileImageUrl()
        );
    }
}
