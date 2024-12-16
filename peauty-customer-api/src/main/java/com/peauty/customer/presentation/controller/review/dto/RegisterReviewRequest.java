package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewRating;

import java.util.List;

public record RegisterReviewRequest(
        ReviewRating reviewRating,
        String contentDetail,
        List<ContentGeneral> contentGeneral,
        List<String> reviewImages
) {

    public RegisterReviewCommand toCommand() {
        return RegisterReviewCommand.builder()
                .reviewRating(reviewRating)
                .contentDetail(contentDetail)
                .contentGeneral(contentGeneral)
                .reviewImages(reviewImages)
                .build();
    }
}
