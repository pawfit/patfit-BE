package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetDesignerReviewsResult;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResponse(
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

    public static GetDesignerReviewsResponse from(GetDesignerReviewsResult result) {
        return new GetDesignerReviewsResponse(
                result.designerId(),
                result.reviews().stream()
                        .map(review -> new ReviewDetails(
                                review.reviewDate(),
                                review.customerNickname(),
                                review.totalGroomingBodyType(),
                                review.totalGroomingFaceType(),
                                review.rating(),
                                review.imageUrls(),
                                review.content()
                        ))
                        .toList()
        );
    }
}