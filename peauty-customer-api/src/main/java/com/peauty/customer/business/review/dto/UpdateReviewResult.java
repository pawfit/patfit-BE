package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;

import java.util.List;

public record UpdateReviewResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        Double reviewRating,
        String contentDetail,
        String contentGeneral,
        List<String> reviewImages
) {

    public static UpdateReviewResult from(Review review) {
        return new UpdateReviewResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating().getValue(),
                review.getContentDetail(),
                review.getContentGeneral().getContentGeneralReview(),
                review.getReviewImages().stream().map(ReviewImage::getImageUrl).toList()
        );
    }
}