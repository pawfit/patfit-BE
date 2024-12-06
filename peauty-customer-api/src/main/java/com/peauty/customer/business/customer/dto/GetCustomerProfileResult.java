package com.peauty.customer.business.customer.dto;

import com.peauty.domain.customer.Customer;

public record GetCustomerProfileResult(
        Long customerId,
        String name,
        String nickname,
        String profileImageUrl,
        String address,
        String phoneNumber
) {

    public static GetCustomerProfileResult from(Customer customer) {
        return new GetCustomerProfileResult(
                customer.getCustomerId(),
                customer.getName(),
                customer.getNickname(),
                customer.getProfileImageUrl(),
                customer.getAddress(),
                customer.getPhoneNumber()
        );
    }
}
