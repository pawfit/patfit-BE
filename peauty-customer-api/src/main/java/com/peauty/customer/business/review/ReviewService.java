package com.peauty.customer.business.review;

import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.customer.business.review.dto.RegisterReviewResult;
import com.peauty.customer.business.review.dto.UpdateReviewCommand;
import com.peauty.customer.business.review.dto.UpdateReviewResult;

public interface ReviewService {

    RegisterReviewResult registerReview(
            Long userId,
            Long puppyId,
            Long threadId,
            Long processId,
            RegisterReviewCommand command
    );

    UpdateReviewResult updateReview(
            Long userId,
            Long puppyId,
            Long threadId,
            Long processId,
            Long reviewId,
            UpdateReviewCommand command
    );

}
