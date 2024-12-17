package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;
import com.peauty.customer.business.review.dto.GetUserReviewsResult;

import java.util.List;

public record GetUserReviewsResponse(
        Long customerId,
        List<GetReviewDetailResponse> reviews
) {
    public static GetUserReviewsResponse from(GetUserReviewsResult result) {
        return new GetUserReviewsResponse(
                result.customerId(),
                result.reviews().stream()
                        .map(GetReviewDetailResponse::from)
                        .toList()
        );
    }
}
