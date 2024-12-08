package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewRating;

public record RegisterReviewResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        ReviewRating reviewRating,
        String contentDetail,
        ContentGeneral contentGeneral
) {

    public static RegisterReviewResult from(Review review) {
        return new RegisterReviewResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating(),
                review.getContentDetail(),
                review.getContentGeneral()
        );
    }

}
