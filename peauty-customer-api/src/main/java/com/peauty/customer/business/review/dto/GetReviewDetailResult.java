package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;

import java.util.List;

public record GetReviewDetailResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGeneral,
        List<String> reviewImages
) {
    public static GetReviewDetailResult from(Review review) {
        return new GetReviewDetailResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating().getValue(),
                review.getContentDetail(),
                review.getContentGeneral().stream()
                        .map(ContentGeneral::getContentGeneralReview)
                        .toList(),
                review.getReviewImages().stream().map(ReviewImage::getImageUrl).toList()
        );
    }
}
