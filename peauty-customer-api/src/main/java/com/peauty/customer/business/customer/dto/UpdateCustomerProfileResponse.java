package com.peauty.customer.business.customer.dto;

public record UpdateCustomerProfileResponse(
        Long customerId,
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl
) {

    public static UpdateCustomerProfileResponse from(UpdateCustomerProfileResult result) {
        return new UpdateCustomerProfileResponse(
                result.customerId(),
                result.name(),
                result.nickname(),
                result.phoneNumber(),
                result.address(),
                result.profileImageUrl()
        );
    }
}
