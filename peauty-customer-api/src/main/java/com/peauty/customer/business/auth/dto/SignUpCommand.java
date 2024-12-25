package com.peauty.customer.business.auth.dto;

import com.peauty.domain.user.SocialPlatform;

public record SignUpCommand(
        String socialId,
        SocialPlatform socialPlatform,
        String name,
        String phoneNumber,
        String address,
        String nickname,
        String profileImageUrl
) {

}
