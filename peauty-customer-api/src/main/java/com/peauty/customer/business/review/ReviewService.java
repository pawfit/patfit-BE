package com.peauty.customer.business.review;

import com.peauty.customer.business.review.dto.*;

public interface ReviewService {

    RegisterReviewResult registerReview(
            Long userId, Long puppyId, Long threadId, Long processId, RegisterReviewCommand command
    );

    UpdateReviewResult updateReview(
            Long userId, Long puppyId, Long threadId, Long processId, Long reviewId, UpdateReviewCommand command
    );

    void deleteReview(Long userId, Long puppyId, Long threadId, Long processId, Long reviewId);

    GetEstimateDataResult getEstimateData(Long userId, Long puppyId, Long threadId, Long processId);

    GetReviewDetailResult getReviewDetail(Long userId, Long puppyId, Long threadId, Long processId, Long reviewId);

    GetDesignerReviewsResult getDesignerReviews(Long designerId);
}
