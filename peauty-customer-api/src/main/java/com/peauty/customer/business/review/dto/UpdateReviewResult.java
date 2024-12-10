package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewRating;

public record UpdateReviewResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        ReviewRating reviewRating,
        String contentDetail,
        ContentGeneral contentGeneral
) {

    public static UpdateReviewResult from(Review review) {
        return new UpdateReviewResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating(),
                review.getContentDetail(),
                review.getContentGeneral()
        );
    }
}