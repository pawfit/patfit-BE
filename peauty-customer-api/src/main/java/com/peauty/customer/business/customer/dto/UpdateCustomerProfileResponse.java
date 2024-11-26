package com.peauty.customer.business.customer.dto;

public record UpdateCustomerProfileResponse(
        Long userId,
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl
) {

    public static UpdateCustomerProfileResponse from(UpdateCustomerProfileResult result) {
        return new UpdateCustomerProfileResponse(
                result.userId(),
                result.name(),
                result.nickname(),
                result.phoneNum(),
                result.address(),
                result.profileImageUrl()
        );
    }
}
