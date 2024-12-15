package com.peauty.customer.business.review.dto;

import java.time.LocalDate;
import java.util.List;

public record GetDesignerReviewsResult(
        Long designerId,
        List<ReviewDetails> reviews
) {
    public record ReviewDetails(
            LocalDate reviewDate,
            String reviewerNickname,
            String groomingStyle,
            Double rating,
            List<String> imageUrls,
            String content
    ) {
        public ReviewDetails(
                LocalDate reviewDate,
                String reviewerNickname,
                String groomingStyle,
                Double rating,
                List<String> imageUrls,
                String content
        ) {
            this.reviewDate = reviewDate;
            this.reviewerNickname = reviewerNickname;
            this.groomingStyle = groomingStyle;
            this.rating = rating;
            this.imageUrls = imageUrls;
            this.content = content;
        }
    }
}