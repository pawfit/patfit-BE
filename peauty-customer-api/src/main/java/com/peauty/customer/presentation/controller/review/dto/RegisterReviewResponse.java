package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.RegisterReviewResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RegisterReviewResponse(
        @Schema(description = "리뷰 ID(PK)", example = "1")
        Long reviewId,
        @Schema(description = "비딩 스레드 ID(FK)", example = "1")
        Long biddingThreadId,
        @Schema(description = "리뷰 평점", example = "TWO")
        Double reviewRating,
        @Schema(description = "리뷰 내용", example = "저희 개가 아주 이뻐졌어요. 그런데 리트리버였는데 왜 닥스훈트가 됐을까요")
        String contentDetail,
        @Schema(description = "선택 리뷰", example = "[\"GOOD_SERVICE\", \"COME_AGAIN\"]")
        List<String> contentGenerals
) {
    public static RegisterReviewResponse from(RegisterReviewResult result) {
        return new RegisterReviewResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating(),
                result.contentDetail(),
                result.contentGenerals()
        );
    }
}
