package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;
import com.peauty.customer.business.review.dto.GetUserReviewsResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetUserReviewsResponse(
        @Schema(description = "커스토머 ID", example = "1")
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
