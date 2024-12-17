package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.GetReviewDetailResult;
import com.peauty.domain.designer.Designer;

import java.time.LocalDate;
import java.util.List;

public record GetReviewDetailResponse(
        Long reviewId,
        Long biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals,
        List<String> reviewImages,
        String groomingStyle,
        String puppyName,
        Long estimateCost,
        LocalDate reviewCreatedAt,
        DesignerProfile designerProfile
) {

    public static GetReviewDetailResponse from(GetReviewDetailResult result){
        return new GetReviewDetailResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
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
            String workspaceName,
            String address
    ) {
    }
}