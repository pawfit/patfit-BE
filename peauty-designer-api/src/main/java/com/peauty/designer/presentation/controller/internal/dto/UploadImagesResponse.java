package com.peauty.designer.presentation.controller.internal.dto;

import java.util.List;

public record UploadImagesResponse(
        List<String> uploadedImageUrl
) {

    public static UploadImagesResponse from(List<String> uploadedImageUrls) {
        return new UploadImagesResponse(
                uploadedImageUrls
        );
    }
}
