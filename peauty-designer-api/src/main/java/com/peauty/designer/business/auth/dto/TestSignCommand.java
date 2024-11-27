package com.peauty.designer.business.auth.dto;

import com.peauty.domain.user.SocialPlatform;

public record TestSignCommand (
        SocialPlatform socialPlatform,
        String idToken,
        String phoneNum,
        String nickname
) {
}
