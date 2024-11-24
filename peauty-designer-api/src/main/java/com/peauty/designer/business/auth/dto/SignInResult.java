package com.peauty.designer.business.auth.dto;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.User;

public record SignInResult(
        String accessToken,
        String refreshToken,
        String socialId,
        SocialPlatform socialPlatform,
        String nickname,
        String profileImageUrl
) {

    public static SignInResult from(SignTokens signTokens, User user) {
        return new SignInResult(
                signTokens.accessToken(),
                signTokens.refreshToken(),
                user.getSocialId(),
                user.getSocialPlatform(),
                user.getNickname(),
                user.getProfileImageUrl()
        );
    }
}