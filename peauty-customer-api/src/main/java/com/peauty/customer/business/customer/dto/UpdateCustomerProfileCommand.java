package com.peauty.customer.business.customer.dto;

public record UpdateCustomerProfileCommand(
        String name,
        String nickname,
        String phoneNumber,
        String address,
        String profileImageUrl

) {
}
