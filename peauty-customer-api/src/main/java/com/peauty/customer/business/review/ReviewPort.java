package com.peauty.customer.business.review;

import com.peauty.domain.review.Review;

public interface ReviewPort {

    Review save(Review review);
//    Review getById(Long reviewId);
}
