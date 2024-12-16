package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.RegisterReviewResult;

public record RegisterReviewResponse(
        Long reviewId,
        Long biddingThreadId,
        Double reviewRating,
        String contentDetail,
        String contentGeneral
) {
    public static RegisterReviewResponse from(RegisterReviewResult result) {
        return new RegisterReviewResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating(),
                result.contentDetail(),
                result.contentGeneral()
        );
    }
}
