package com.peauty.customer.business.addy.dto;

import lombok.Builder;

@Builder
public record CreateMaskingResult(
        Long maskingId,
        String maskingImageUrl
) {
}
