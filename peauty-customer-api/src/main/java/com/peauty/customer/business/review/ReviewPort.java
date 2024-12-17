package com.peauty.customer.business.review;

import com.peauty.domain.review.Review;

import java.util.List;

public interface ReviewPort {

    Review registerNewReview(Review review);
//    Review getById(Long reviewId);
    Review findReviewById(Long reviewId);

    Review saveReview(Review review);

    void deleteReviewById(Long reviewId);

    Review getReviewByIdAndBiddingThreadId(Long id, Long biddingThreadId);
    Boolean existsByBiddingThreadId(Long biddingThreadId);

    List<Review> findReviewsByDesignerId(Long designerId);

    List<Review> findReviewsByCustomerId(Long customerId);
}
