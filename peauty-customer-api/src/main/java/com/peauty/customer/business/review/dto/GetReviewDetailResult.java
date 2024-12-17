package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;

import java.time.LocalDate;
import java.util.List;

public record GetReviewDetailResult(
        Review.ID reviewId,
        BiddingThread.ID biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals,
        List<String> reviewImages,
        String groomingStyle,
        String puppyName,
        Long estimateCost,
        LocalDate reviewCreatedAt,
        Designer.Profile designerProfile
) {
    public static GetReviewDetailResult from(Review review, String puppyName, Long estimateCost, String groomingStyle, Designer.Profile designerProfile) {
        return new GetReviewDetailResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                review.getReviewRating().getValue(),
                review.getContentDetail(),
                review.getContentGenerals().stream()
                        .map(ContentGeneral::getContentGeneralReview)
                        .toList(),
                review.getReviewImages().stream().map(ReviewImage::getImageUrl).toList(),
                groomingStyle,
                puppyName,
                estimateCost,
                review.getReviewCreatedAt(),
                designerProfile
        );
    }
}
