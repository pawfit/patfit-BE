package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;

import java.util.List;

public record RegisterReviewResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals
) {

    public static RegisterReviewResult from(Review review) {
        return new RegisterReviewResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating().getValue(),
                review.getContentDetail(),
                review.getContentGenerals().stream()
                        .map(ContentGeneral::getContentGeneralReview)
                        .toList()
        );
    }

}
