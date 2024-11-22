package com.peauty.designer.business.auth.dto;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;

public record SignUpResult(
        Long userId,
        String accessToken,
        String refreshToken
) {
    public static SignUpResult from(SignTokens signTokens, User user) {
        return new SignUpResult(
                user.getUserId(),
                signTokens.accessToken(),
                signTokens.refreshToken()
        );
    }
}
