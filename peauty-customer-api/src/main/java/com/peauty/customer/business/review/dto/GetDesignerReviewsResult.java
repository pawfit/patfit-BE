package com.peauty.customer.business.review.dto;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResult(
        Long designerId,
        List<ReviewDetails> reviews
) {
    public record ReviewDetails(
            LocalDate reviewDate,
            String customerNickname,
            String totalGroomingBodyType,
            String totalGroomingFaceType,
            Double rating,
            List<String> imageUrls,
            String content
    ) {}
}