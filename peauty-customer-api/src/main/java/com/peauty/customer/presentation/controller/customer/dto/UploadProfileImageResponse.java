package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.UploadProfileImageResult;

public record UploadProfileImageResponse(
        Long userId,
        String uploadedProfileImageUrl
) {

    public static UploadProfileImageResponse from(UploadProfileImageResult result) {
        return new UploadProfileImageResponse(
                result.userId(),
                result.uploadedProfileImageUrl()
        );
    }
}
