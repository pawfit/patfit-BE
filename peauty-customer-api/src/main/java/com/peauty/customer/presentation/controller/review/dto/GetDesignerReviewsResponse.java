package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetDesignerReviewsResult;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResponse(
        Long designerId,
        List<ReviewDetails> reviews
) {
    public record ReviewDetails(
            Long reviewId,
            LocalDate reviewDate,
            String reviewerNickname,
            String groomingStyle,
            Double rating,
            List<String> imageUrls,
            String content
    ) {}

    public static GetDesignerReviewsResponse from(GetDesignerReviewsResult result) {
        return new GetDesignerReviewsResponse(
                result.designerId(),
                result.reviews().stream()
                        .map(review -> new ReviewDetails(
                                review.reviewId(),
                                review.reviewDate(),
                                review.reviewerNickname(),
                                review.groomingStyle(),
                                review.rating(),
                                review.imageUrls(),
                                review.content()
                        ))
                        .toList()
        );
    }
}