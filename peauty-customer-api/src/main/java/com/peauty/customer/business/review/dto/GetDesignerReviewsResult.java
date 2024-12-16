package com.peauty.customer.business.review.dto;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResult(
        Long designerId,
        List<ReviewDetails> reviews
) {
    // TODO: 다른 API 가능성에 의한 reviewId 넣기
    public record ReviewDetails(
            Long reviewId,
            LocalDate reviewDate,
            String reviewerNickname,
            String groomingStyle,
            Double rating,
            List<String> imageUrls,
            String content
    ) {
        public ReviewDetails(
                Long reviewId,
                LocalDate reviewDate,
                String reviewerNickname,
                String groomingStyle,
                Double rating,
                List<String> imageUrls,
                String content
        ) {
            this.reviewId = reviewId;
            this.reviewDate = reviewDate;
            this.reviewerNickname = reviewerNickname;
            this.groomingStyle = groomingStyle;
            this.rating = rating;
            this.imageUrls = imageUrls;
            this.content = content;
        }
    }
}