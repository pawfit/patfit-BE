package com.peauty.customer.business.customer.dto;

public record UpdateCustomerProfileRequest(
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl
) {

    public UpdateCustomerProfileCommand toCommand() {
        return new UpdateCustomerProfileCommand(
                this.name,
                this.nickname,
                this.phoneNum,
                this.address,
                this.profileImageUrl
        );
    }
}