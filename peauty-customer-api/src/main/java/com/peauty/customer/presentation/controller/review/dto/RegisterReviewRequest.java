package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewRating;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RegisterReviewRequest(
        @Schema(description = "리뷰 평점 ZERO, ZERO_POINT_FIVE, ONE, ONE_POINT_FIVE, TWO, TWO_POINT_FIVE, THREE, THREE_POINT_FIVE, FOUR, FOUR_POINT_FIVE, FIVE", example = "TWO")
        ReviewRating reviewRating,
        @Schema(description = "리뷰 내용", example = "저희 개가 아주 이뻐졌어요. 그런데 리트리버였는데 왜 닥스훈트가 됐을까요")
        String contentDetail,
        @Schema(description = "선택 리뷰(다중 선택 가능) GOOD_SERVICE, COME_AGAIN, KIND, MYPICK", example = "[\"GOOD_SERVICE\", \"COME_AGAIN\"]")
        List<ContentGeneral> contentGenerals,
        @Schema(description = "사진", example = "[\"https://peauty.s3.ap-northeast-2.amazonaws.com/images/b24c84d4-0295-43e0-bdb5-238889f3b5c8.jpg\"]")
        List<String> reviewImages
) {

    public RegisterReviewCommand toCommand() {
        return RegisterReviewCommand.builder()
                .reviewRating(reviewRating)
                .contentDetail(contentDetail)
                .contentGenerals(contentGenerals)
                .reviewImages(reviewImages)
                .build();
    }
}
