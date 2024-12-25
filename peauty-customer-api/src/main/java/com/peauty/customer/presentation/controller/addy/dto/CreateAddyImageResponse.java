package com.peauty.customer.presentation.controller.addy.dto;

import com.peauty.customer.business.addy.dto.CreateAddyImageResult;
import lombok.Builder;

@Builder
public record CreateAddyImageResponse(
        Long addyId,
        Long puppyId,
        String addyImageUrl
) {
    public static CreateAddyImageResponse from(CreateAddyImageResult result) {
        return CreateAddyImageResponse.builder()
                .addyId(result.addyId())
                .puppyId(result.puppyId())
                .addyImageUrl(result.addyImageUrl())
                .build();
    }
}
