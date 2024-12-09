package com.peauty.customer.implementaion.review;

import com.peauty.customer.business.review.ReviewPort;
import com.peauty.domain.review.Review;
import com.peauty.persistence.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewAdapter implements ReviewPort {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Override
    public Review registerNewReview(Review review){
        ReviewEntity registerReviewEntity = reviewRepository.save(
                ReviewMapper.toReviewEntity(review)
        );
        List<ReviewImageEntity> reviewImageEntities = review.getReviewImages().stream()
                .map(image -> ReviewMapper.toReviewImageEntity(image, registerReviewEntity))
                .toList();
        List<ReviewImageEntity> registerReviewImageEntities = reviewImageRepository.saveAll(reviewImageEntities);
        return ReviewMapper.toReviewDomain(registerReviewEntity, registerReviewImageEntities);
    }

    @Override
    public Review findReviewById(Long reviewId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found")); // Replace with proper exception
        List<ReviewImageEntity> reviewImageEntities = reviewImageRepository.findAllByReviewId(reviewId);
        return ReviewMapper.toReviewDomain(reviewEntity, reviewImageEntities);
    }

    @Override
    public Review saveReview(Review review) {
        ReviewEntity updatedReviewEntity = reviewRepository.save(
                ReviewMapper.toReviewEntity(review)
        );
        List<ReviewImageEntity> updatedReviewImageEntities = review.getReviewImages().stream()
                .map(image -> ReviewMapper.toReviewImageEntity(image, updatedReviewEntity))
                .toList();
        reviewImageRepository.saveAll(updatedReviewImageEntities);
        return ReviewMapper.toReviewDomain(updatedReviewEntity, updatedReviewImageEntities);
    }

    @Override
    @Transactional
    public void deleteReviewById(Long reviewId) {
        // 1. 연결된 ReviewImage 삭제
        List<ReviewImageEntity> reviewImages = reviewImageRepository.findAllByReviewId(reviewId);
        reviewImageRepository.deleteAll(reviewImages);
        // 2. Review 삭제
        reviewRepository.deleteById(reviewId);
    }

}
