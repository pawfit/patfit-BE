package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;

import java.util.List;

public record GetReviewDetailResponse(
        Long reviewId,
        Long biddingThreadId,
        String reviewRating,
        String contentDetail,
        String contentGeneral,
        List<String> reviewImages
) {

    public static GetReviewDetailResponse from(GetReviewDetailResult result){
        return new GetReviewDetailResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating().name(),
                result.contentDetail(),
                result.contentGeneral().name(),
                result.reviewImages()
        );
    }
}