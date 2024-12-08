package com.peauty.customer.business.review;

import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.customer.business.review.dto.RegisterReviewResult;

public interface ReviewService {

    RegisterReviewResult registerReview(
            Long userId,
            Long puppyId,
            Long threadId,
            Long processId,
            RegisterReviewCommand command
    );


}
