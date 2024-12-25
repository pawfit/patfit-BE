package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Designer;

public record UploadProfileImageResult(
        Long designerId,
        String uploadedProfileImageUrl
) {

    public static UploadProfileImageResult from(Designer designer) {
        return new UploadProfileImageResult(
                designer.getDesignerId(),
                designer.getProfileImageUrl()
        );
    }
}
