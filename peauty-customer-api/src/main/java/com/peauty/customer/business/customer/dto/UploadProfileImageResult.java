package com.peauty.customer.business.customer.dto;

import com.peauty.domain.user.User;

public record UploadProfileImageResult(
        Long userId,
        String uploadedProfileImageUrl
) {

    public static UploadProfileImageResult from(User user) {
        return new UploadProfileImageResult(
                user.getUserId(),
                user.getProfileImageUrl()
        );
    }
}
