package com.peauty.designer.business.auth.dto;

import com.peauty.domain.user.SocialPlatform;

public record SignUpCommand(
        String socialId,
        SocialPlatform socialPlatform,
        String name,
        String phoneNum,
        String address,
        String nickname,
        String profileImageUrl
) {

}
