package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.UpdateReviewResult;

import java.util.List;

public record UpdateReviewResponse(
        Long reviewId,
        Long biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals,
        List<String> reviewImages
) {
    public static UpdateReviewResponse from(UpdateReviewResult result) {
        return new UpdateReviewResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating(),
                result.contentDetail(),
                result.contentGenerals(),
                result.reviewImages()

        );
    }
}