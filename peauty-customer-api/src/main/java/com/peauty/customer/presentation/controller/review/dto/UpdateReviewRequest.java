package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.UpdateReviewCommand;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UpdateReviewRequest(
        @Schema(description = "리뷰 평점 ZERO, ZERO_POINT_FIVE, ONE, ONE_POINT_FIVE, TWO, TWO_POINT_FIVE, THREE, THREE_POINT_FIVE, FOUR, FOUR_POINT_FIVE, FIVE", example = "FOUR")
        ReviewRating reviewRating,
        @Schema(description = "리뷰 내용", example = "뽀송뽀송해진 우리 개 어때영")
        String contentDetail,
        @Schema(description = "선택 리뷰(다중 선택 가능) GOOD_SERVICE, COME_AGAIN, KIND, MYPICK", example = "[\"GOOD_SERVICE\", \"COME_AGAIN\"]")
        List<ContentGeneral> contentGenerals,
        @Schema(description = "사진", example = "[\"https://peauty.s3.ap-northeast-2.amazonaws.com/images/b24c84d4-0295-43e0-bdb5-238889f3b5c8.jpg\"]")
        List<String> reviewImageUrls
) {

    public UpdateReviewCommand toCommand() {
        return UpdateReviewCommand.builder()
                .reviewRating(reviewRating)
                .contentDetail(contentDetail)
                .contentGenerals(contentGenerals)
                .reviewImages(
                        reviewImageUrls.stream()
                                .map(url -> ReviewImage.builder().imageUrl(url).build())
                                .toList()
                )
                .build();
    }
}