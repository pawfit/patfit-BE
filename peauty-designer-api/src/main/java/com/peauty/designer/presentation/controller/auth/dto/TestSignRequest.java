package com.peauty.designer.presentation.controller.auth.dto;

import com.peauty.designer.business.auth.dto.TestSignCommand;
import com.peauty.domain.user.SocialPlatform;

public record TestSignRequest(
        SocialPlatform socialPlatform,
        String idToken,
        String phoneNum,
        String nickname
) {

    public TestSignCommand toCommand() {
        return new TestSignCommand(
                socialPlatform,
                idToken,
                phoneNum,
                nickname
        );
    }
}
