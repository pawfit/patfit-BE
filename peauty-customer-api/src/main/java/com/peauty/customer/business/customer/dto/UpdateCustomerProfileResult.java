package com.peauty.customer.business.customer.dto;

import com.peauty.domain.user.UserProfile;

public record UpdateCustomerProfileResult(
        Long userId,
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl
) {

    public static UpdateCustomerProfileResult from(UserProfile userProfile) {
        return new UpdateCustomerProfileResult(
                userProfile.getUserId(),
                userProfile.getName(),
                userProfile.getNickname(),
                userProfile.getPhoneNum(),
                userProfile.getAddress(),
                userProfile.getProfileImageUrl()
        );
    }
}
