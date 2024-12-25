package com.peauty.customer.business.review.dto;

import java.util.List;

public record GetUserReviewsResult(
        Long customerId,
        List<GetReviewDetailResult> reviews
) {
    public static GetUserReviewsResult from(Long customerId, List<GetReviewDetailResult> reviews) {
        return new GetUserReviewsResult(customerId, reviews);
    }
}