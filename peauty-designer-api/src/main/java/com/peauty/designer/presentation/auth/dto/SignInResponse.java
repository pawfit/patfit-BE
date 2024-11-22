package com.peauty.designer.presentation.auth.dto;


import com.peauty.designer.business.auth.dto.SignInResult;

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
