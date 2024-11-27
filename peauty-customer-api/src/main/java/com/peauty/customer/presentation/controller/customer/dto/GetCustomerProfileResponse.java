package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.GetCustomerProfileResult;

public record GetCustomerProfileResponse(
        Long customerId,
        String name,
        String nickname,
        String profileImageUrl,
        String address
) {

    public static GetCustomerProfileResponse from(GetCustomerProfileResult result) {
        return new GetCustomerProfileResponse(
                result.customerId(),
                result.name(),
                result.nickname(),
                result.profileImageUrl(),
                result.address()
        );
    }
}

