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
        Long biddingProcessId,
        Long puppyId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals,
        List<String> reviewImages,
        String groomingStyle,
        String puppyName,
        Long estimateCost,
        LocalDate reviewCreatedAt,
        DesignerSimpleProfile designerProfile
) {

    public record DesignerSimpleProfile(
            String workspaceName,
            String address){

    }

// TODO: 추후 puppyName을 Puppy 내에서 받아오게 리팩토링 예정, estimateCost 등도 도메인 내에서 받아올 수 있도록 수정 예정.
    public static GetReviewDetailResult from(Review review, Puppy puppy, String puppyName, Long estimateCost, String groomingStyle, Designer.DesignerProfile designerProfile, Long biddingProcessId) {
        return new GetReviewDetailResult(
                review.getSavedReviewId(),
                review.getThreadId(),
                biddingProcessId,
                puppy.getPuppyId(),
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
                new DesignerSimpleProfile(
                        designerProfile.workspaceName(),
                        designerProfile.address()
                )
        );
    }
}
