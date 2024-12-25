package com.peauty.customer.business.customer.dto;

import com.peauty.domain.customer.Customer;

public record UploadProfileImageResult(
        Long customerId,
        String uploadedProfileImageUrl
) {

    public static UploadProfileImageResult from(Customer customer) {
        return new UploadProfileImageResult(
                customer.getCustomerId(),
                customer.getProfileImageUrl()
        );
    }
}
