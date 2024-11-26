package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.GetCustomerProfileResult;
import com.peauty.domain.user.UserProfile;

public record GetCustomerProfileResponse(
        Long userId,
        String name,
        String nickname,
        String profileImageUrl,
        String address
) {

    public static GetCustomerProfileResponse from(GetCustomerProfileResult result) {
        return new GetCustomerProfileResponse(
                result.userId(),
                result.name(),
                result.nickname(),
                result.profileImageUrl(),
                result.address()
        );
    }
}

