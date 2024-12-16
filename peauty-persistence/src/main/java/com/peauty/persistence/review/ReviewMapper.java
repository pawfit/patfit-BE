package com.peauty.persistence.review;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;

import java.time.LocalDate;
import java.util.List;

public class ReviewMapper {

    /*private ReviewMapper() {
        // private 생성자로 인스턴스화 방지
    }*/
    public static ReviewEntity toReviewEntity(Review domain){
        return ReviewEntity.builder()
                .id(domain.getId()
                        .map(Review.ID::value)
                        .orElse(null))
                .biddingThreadId(domain.getThreadId().value())
                .reviewRating(domain.getReviewRating())
                .contentDetail(domain.getContentDetail())
                .contentGeneral(domain.getContentGeneral())
                .build();
    }

    public static ReviewImageEntity toReviewImageEntity(ReviewImage domain, ReviewEntity reviewEntity){
        return ReviewImageEntity.builder()
                .id(domain.getId()
                        .map(ReviewImage.ID::value)
                        .orElse(null)
                )
                .review(reviewEntity)
                .reviewImageUrl(domain.getImageUrl())
                .build();
    }

    public static Review toReviewDomain(ReviewEntity entity, List<ReviewImageEntity> reviewImageEntities){
        List<ReviewImage> reviewImages = reviewImageEntities.stream()
                .map(ReviewMapper::toReviewImageDomain)
                .toList();

        return Review.builder()
                .id(new Review.ID(entity.getId()))
                .threadId(new BiddingThread.ID(entity.getBiddingThreadId()))
                .reviewRating(entity.getReviewRating())
                .contentDetail(entity.getContentDetail())
                .contentGeneral(entity.getContentGeneral())
                .reviewImages(reviewImages)
                // TODO: null이 아닌데 null 체크를 해야 하는 것이 매우 이상.
                .reviewCreatedAt(entity.getCreatedAt() != null ? LocalDate.from(entity.getCreatedAt()) : null)
                .build();

    }

    public static ReviewImage toReviewImageDomain(ReviewImageEntity entity){
        return ReviewImage.builder()
                .id(new ReviewImage.ID(entity.getId()))
                .reviewId(new Review.ID(entity.getReview().getId()))
                .imageUrl(entity.getReviewImageUrl())
                .build();
    }

    public static Review toReviewDomain(
            ReviewEntity reviewEntity,
            String reviewerNickname,
            EstimateProposal proposal,
            List<ReviewImageEntity> imageEntities
    ) {
        String groomingStyle = proposal.getSimpleGroomingStyle();

        return Review.builder()
                .id(new Review.ID(reviewEntity.getId()))
                .reviewCreatedAt(reviewEntity.getCreatedAt().toLocalDate())
                .reviewerNickname(reviewerNickname)
                .groomingStyle(groomingStyle)
                .contentDetail(reviewEntity.getContentDetail())
                .reviewImages(imageEntities.stream()
                        .map(imageEntity -> ReviewImage.builder()
                                .imageUrl(imageEntity.getReviewImageUrl())
                                .build())
                        .toList())
                .reviewRating(reviewEntity.getReviewRating())
                .build();


    }

}

