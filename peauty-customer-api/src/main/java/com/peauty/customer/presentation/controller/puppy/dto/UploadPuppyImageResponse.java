package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.UploadPuppyImageResult;

public record UploadPuppyImageResponse(
        Long customerId,
        Long puppyId,
        String uploadedPuppyImageUrl
) {
    public static UploadPuppyImageResponse from(UploadPuppyImageResult result) {
        return new UploadPuppyImageResponse(
                result.customerId(),
                result.puppyId(),
                result.uploadedPuppyImageUrl()
        );
    }
}
