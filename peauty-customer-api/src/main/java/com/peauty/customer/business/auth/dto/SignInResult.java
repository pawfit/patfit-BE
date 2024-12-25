package com.peauty.customer.business.auth.dto;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialPlatform;

public record SignInResult(
        String accessToken,
        String refreshToken,
        String socialId,
        SocialPlatform socialPlatform,
        String nickname,
        String profileImageUrl
) {

    public static SignInResult from(SignTokens signTokens, Customer customer) {
        return new SignInResult(
                signTokens.accessToken(),
                signTokens.refreshToken(),
                customer.getSocialId(),
                customer.getSocialPlatform(),
                customer.getNickname(),
                customer.getProfileImageUrl()
        );
    }
}
