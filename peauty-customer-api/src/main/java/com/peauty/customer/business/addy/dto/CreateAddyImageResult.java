package com.peauty.customer.business.addy.dto;

import com.peauty.domain.addy.AddyImage;
import lombok.Builder;

@Builder
public record CreateAddyImageResult(
        Long addyId,
        Long userId,
        Long puppyId,
        String addyImageUrl
) {
    public static CreateAddyImageResult from(AddyImage addyImage) {
        return CreateAddyImageResult.builder()
                .addyId(addyImage.getAddyId())
                .addyImageUrl(addyImage.getAddyImageUrl())
                .build();
    }

    public static CreateAddyImageResult from(CreateMaskingResult result) {
        return CreateAddyImageResult.builder()
                .addyImageUrl(result.maskingImageUrl())
                .build();
    }
}
