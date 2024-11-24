package com.peauty.designer.presentation.controller.auth.dto;


import com.peauty.designer.business.auth.dto.SignUpResult;

public record SignUpResponse(
        String accessToken,
        String refreshToken
) {

    public static SignUpResponse from(SignUpResult result) {
        return new SignUpResponse(
                result.accessToken(),
                result.refreshToken()
        );
    }
}
