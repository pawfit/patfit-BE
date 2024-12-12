package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.UpdateReviewResult;

import java.util.List;

public record UpdateReviewResponse(
        Long reviewId,
        Long biddingThreadId,
        String reviewRating,
        String contentDetail,
        String contentGeneral,
        List<String> reviewImages
) {
    public static UpdateReviewResponse from(UpdateReviewResult result) {
        return new UpdateReviewResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating().name(),
                result.contentDetail(),
                result.contentGeneral().name(),
                result.reviewImages()

        );
    }
}