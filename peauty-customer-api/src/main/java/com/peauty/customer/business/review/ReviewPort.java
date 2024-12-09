package com.peauty.customer.business.review;

import com.peauty.domain.review.Review;

public interface ReviewPort {

    Review registerNewReview(Review review);
//    Review getById(Long reviewId);
    Review findReviewById(Long reviewId);

    Review saveReview(Review review);

    void deleteReviewById(Long reviewId);
}
