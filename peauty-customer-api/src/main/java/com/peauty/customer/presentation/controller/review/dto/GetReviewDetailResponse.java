package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record GetReviewDetailResponse(
        @Schema(description = "리뷰 ID(PK)", example = "1")
        Long reviewId,
        @Schema(description = "비딩 스레드 ID(FK)", example = "1")
        Long biddingThreadId,
        @Schema(description = "리뷰 프로세스 ID(FK)", example = "1")
        Long biddingProcessId,
        @Schema(description = "리뷰 평점", example = "FOUR")
        Double reviewRating,
        @Schema(description = "리뷰 내용", example = "그럭저럭이었어요.")
        String contentDetail,
        @Schema(description = "선택 리뷰", example = "[\"GOOD_SERVICE\", \"COME_AGAIN\"]")
        List<String> contentGenerals,
        @Schema(description = "사진", example = "[\"https://peauty.s3.ap-northeast-2.amazonaws.com/images/b24c84d4-0295-43e0-bdb5-238889f3b5c8.jpg\"]")
        List<String> reviewImages,
        @Schema(description = "미용 종류", example = "알머리 + 클리핑컷")
        String groomingStyle,
        @Schema(description = "강아지 이름", example = "꾸미")
        String puppyName,
        @Schema(description = "비용", example = "50000")
        Long estimateCost,
        @Schema(description = "리뷰 작성 일자", example = "2022-01-01")
        LocalDate reviewCreatedAt,
        DesignerProfile designerProfile
) {

    public static GetReviewDetailResponse from(GetReviewDetailResult result){
        return new GetReviewDetailResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.biddingProcessId(),
                result.reviewRating(),
                result.contentDetail(),
                result.contentGenerals(),
                result.reviewImages(),
                result.groomingStyle(),
                result.puppyName(),
                result.estimateCost(),
                result.reviewCreatedAt(),
                new DesignerProfile(
                        result.designerProfile().workspaceName(),
                        result.designerProfile().address()
                )
        );
    }

    public record DesignerProfile(
            @Schema(description = "미용실 이름", example = "스타일하우스")
            String workspaceName,
            @Schema(description = "큰 주소", example = "서울시 강남구 역삼동")
            String address
    ) {
    }
}