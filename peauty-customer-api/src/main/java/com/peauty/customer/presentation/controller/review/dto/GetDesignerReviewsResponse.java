package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetDesignerReviewsResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResponse(
        Long designerId,
        List<ReviewDetails> reviews
) {
    public record ReviewDetails(
            @Schema(description = "리뷰 ID(PK)", example = "1")
            Long reviewId,
            @Schema(description = "리뷰 작성 일자", example = "2022-01-01")
            LocalDate reviewDate,
            @Schema(description = "리뷰 작성자 닉네임", example = "화이팅")
            String reviewerNickname,
            @Schema(description = "미용 종류", example = "알머리 + 클리핑컷")
            String groomingStyle,
            @Schema(description = "리뷰 평점", example = "FOUR")
            Double rating,
            @Schema(description = "사진", example = "[\"https://peauty.s3.ap-northeast-2.amazonaws.com/images/b24c84d4-0295-43e0-bdb5-238889f3b5c8.jpg\"]")
            List<String> imageUrls,
            @Schema(description = "리뷰 내용", example = "그럭저럭이었어요.")
            String content
    ) {}

    public static GetDesignerReviewsResponse from(GetDesignerReviewsResult result) {
        return new GetDesignerReviewsResponse(
                result.designerId(),
                result.reviews().stream()
                        .map(review -> new ReviewDetails(
                                review.reviewId(),
                                review.reviewDate(),
                                review.reviewerNickname(),
                                review.groomingStyle(),
                                review.rating(),
                                review.imageUrls(),
                                review.content()
                        ))
                        .toList()
        );
    }
}