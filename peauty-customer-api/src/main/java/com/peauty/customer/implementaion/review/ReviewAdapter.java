package com.peauty.customer.implementaion.review;

import com.peauty.customer.business.review.ReviewPort;
import com.peauty.domain.review.Review;
import com.peauty.persistence.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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


}
