package com.peauty.customer.business.customer.dto;

import com.peauty.domain.user.UserProfile;

public record GetCustomerProfileResult(
        Long userId,
        String name,
        String nickname,
        String profileImageUrl,
        String address
) {

    public static GetCustomerProfileResult from(UserProfile userProfile) {
        return new GetCustomerProfileResult(
                userProfile.getUserId(),
                userProfile.getName(),
                userProfile.getNickname(),
                userProfile.getProfileImageUrl(),
                userProfile.getAddress()
        );
    }
}
