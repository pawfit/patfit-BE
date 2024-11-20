package com.peauty.customer.presentation.controller.auth.dto;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.domain.user.OidcProviderType;

public record SignUpRequest(
        String socialId,
        OidcProviderType socialPlatform,
        String name,
        String phoneNum,
        String region,
        String nickname,
        String profileImageUrl
) {

    public SignUpCommand toCommand() {
        return new SignUpCommand(
                this.socialId,
                this.socialPlatform,
                this.name,
                this.phoneNum,
                this.region,
                this.nickname,
                this.profileImageUrl
        );
    }
}
