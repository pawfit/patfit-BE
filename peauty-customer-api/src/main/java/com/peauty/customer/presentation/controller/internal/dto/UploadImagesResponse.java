package com.peauty.customer.presentation.controller.internal.dto;

import java.util.List;

public record UploadImagesResponse(
        List<String> uploadedImageUrls
) {

    public static UploadImagesResponse from(List<String> uploadedImageUrls) {
        return new UploadImagesResponse(
                uploadedImageUrls
        );
    }
}