package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.UpdateReviewResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UpdateReviewResponse(
        @Schema(description = "리뷰 ID(PK)", example = "1")
        Long reviewId,
        @Schema(description = "비딩 스레드 ID(FK)", example = "1")
        Long biddingThreadId,
        @Schema(description = "리뷰 평점", example = "FOUR")
        Double reviewRating,
        @Schema(description = "리뷰 내용", example = "뽀송뽀송해진 우리 개 어때영")
        String contentDetail,
        @Schema(description = "선택 리뷰", example = "KIND")
        List<String> contentGenerals,
        @Schema(description = "사진", example = "https://peauty.s3.ap-northeast-2.amazonaws.com/images/b24c84d4-0295-43e0-bdb5-238889f3b5c8.jpg")
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