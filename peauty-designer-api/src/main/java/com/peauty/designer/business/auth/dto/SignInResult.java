package com.peauty.designer.business.auth.dto;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialPlatform;

public record SignInResult(
        String accessToken,
        String refreshToken,
        String socialId,
        SocialPlatform socialPlatform,
        String nickname,
        String profileImageUrl
) {

    public static SignInResult from(SignTokens signTokens, Designer designer) {
        return new SignInResult(
                signTokens.accessToken(),
                signTokens.refreshToken(),
                designer.getSocialId(),
                designer.getSocialPlatform(),
                designer.getNickname(),
                designer.getProfileImageUrl()
        );
    }
}

