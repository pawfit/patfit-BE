package com.peauty.customer.business.auth.dto;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;

public record SignUpResult(
        Long id,
        String accessToken,
        String refreshToken
) {
    public static SignUpResult from(SignTokens signTokens, User user) {
        return new SignUpResult(
                user.getId(),
                signTokens.accessToken(),
                signTokens.refreshToken()
        );
    }
}
