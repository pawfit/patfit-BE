package com.peauty.designer.business.auth.dto;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.token.SignTokens;

public record SignUpResult(
        Long designerId,
        String accessToken,
        String refreshToken
) {
    public static SignUpResult from(SignTokens signTokens, Designer designer) {
        return new SignUpResult(
                designer.getDesignerId(),
                signTokens.accessToken(),
                signTokens.refreshToken()
        );
    }
}
