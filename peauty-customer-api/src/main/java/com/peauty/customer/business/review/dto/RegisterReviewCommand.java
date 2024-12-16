package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;
import lombok.Builder;

import java.util.List;

@Builder
public record RegisterReviewCommand(
        ReviewRating reviewRating, // ReviewRating
        String contentDetail,
        List<ContentGeneral> contentGenerals,
        List<String> reviewImages
) {

    public Review toReview(Long threadId){
        return Review.builder()
                .id(null)
                .threadId(new BiddingThread.ID(threadId))
                .reviewRating(reviewRating)
                .contentDetail(contentDetail)
                .contentGenerals(contentGenerals)
                .reviewImages(reviewImages.stream()
                        .map(imageUrl -> ReviewImage.builder()
                                .id(null)
                                .reviewId(null)
                                .imageUrl(imageUrl)
                                .build())
                        .toList())
                .build();
    }
}
