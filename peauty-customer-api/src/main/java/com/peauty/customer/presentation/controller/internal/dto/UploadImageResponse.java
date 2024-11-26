package com.peauty.customer.presentation.controller.internal.dto;

public record UploadImageResponse(
        String uploadedImageUrl
) {

    public static UploadImageResponse from(String uploadedImageUrl) {
        return new UploadImageResponse(
                uploadedImageUrl
        );
    }
}
