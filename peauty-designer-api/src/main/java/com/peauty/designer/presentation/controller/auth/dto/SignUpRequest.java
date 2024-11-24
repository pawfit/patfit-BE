package com.peauty.designer.presentation.controller.auth.dto;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.domain.user.SocialPlatform;

public record SignUpRequest(
        String socialId,
        SocialPlatform socialPlatform,
        String name,
        String phoneNum,
        String address,
        String nickname,
        String profileImageUrl
) {

    public SignUpCommand toCommand() {
        return new SignUpCommand(
                this.socialId,
                this.socialPlatform,
                this.name,
                this.phoneNum,
                this.address,
                this.nickname,
                this.profileImageUrl
        );
    }
}
