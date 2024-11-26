package com.peauty.customer.business.customer.dto;

import com.peauty.domain.user.UserProfile;

public record UpdateCustomerProfileCommand(
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl

) {

    public UserProfile toUserProfileToUpdate(Long userId) {
        return UserProfile.builder()
                .userId(userId)
                .name(this.name)
                .nickname(this.nickname)
                .phoneNum(this.phoneNum)
                .address(this.address)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
