package com.peauty.customer.business.customer.dto;

import com.peauty.domain.customer.Customer;

public record UpdateCustomerProfileResult(
        Long customerId,
        String name,
        String nickname,
        String phoneNumber,
        String address,
        String profileImageUrl
) {

    public static UpdateCustomerProfileResult from(Customer customer) {
        return new UpdateCustomerProfileResult(
                customer.getCustomerId(),
                customer.getName(),
                customer.getNickname(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getProfileImageUrl()
        );
    }
}
