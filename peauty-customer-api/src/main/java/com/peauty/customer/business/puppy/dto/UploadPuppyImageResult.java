package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Puppy;

public record UploadPuppyImageResult(
        Long customerId,
        Long puppyId,
        String uploadedPuppyImageUrl
) {
    public static UploadPuppyImageResult from(Puppy puppy) {
        return new UploadPuppyImageResult(
                puppy.getCustomerId(),
                puppy.getPuppyId(),
                puppy.getProfileImageUrl()
        );
    }
}
