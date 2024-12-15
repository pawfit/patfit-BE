package com.peauty.customer.business.addy.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateAddyImageCommand(
        MultipartFile image,
        int x1, int y1,
        int x2, int y2,
        int x3, int y3,
        int x4, int y4,
        String prompt,
        String bearerToken
){


    public CreateMaskingResult toCreateMaskingResult() {
        return CreateMaskingResult.builder()
                .build();
    }
}
