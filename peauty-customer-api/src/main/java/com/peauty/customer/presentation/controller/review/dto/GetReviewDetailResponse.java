package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;
import com.peauty.domain.review.ContentGeneral;

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
                result.reviewRating().toString(),
                result.contentDetail(),
                result.contentGeneral(),
                result.reviewImages()
        );
    }
}
