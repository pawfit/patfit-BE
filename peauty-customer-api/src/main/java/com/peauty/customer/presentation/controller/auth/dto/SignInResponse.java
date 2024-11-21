package com.peauty.customer.presentation.controller.auth.dto;

import com.peauty.customer.business.auth.dto.SignInResult;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {

    public static SignInResponse from(SignInResult result) {
        return new SignInResponse(
                result.accessToken(),
                result.refreshToken()
        );
    }
}
