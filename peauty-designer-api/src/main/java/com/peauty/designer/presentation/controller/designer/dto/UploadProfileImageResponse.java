package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.UploadProfileImageResult;

public record UploadProfileImageResponse(
        Long designerId,
        String uploadedProfileImageUrl
) {

    public static UploadProfileImageResponse from(UploadProfileImageResult result) {
        return new UploadProfileImageResponse(
                result.designerId(),
                result.uploadedProfileImageUrl()
        );
    }
}
