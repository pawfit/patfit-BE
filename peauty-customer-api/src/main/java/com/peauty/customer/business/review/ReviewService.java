package com.peauty.customer.business.review;

import com.peauty.customer.business.review.dto.*;

public interface ReviewService {

    RegisterReviewResult registerReview(
            Long userId, Long puppyId, Long processId, Long threadId, RegisterReviewCommand command
    );

    UpdateReviewResult updateReview(
            Long userId, Long puppyId, Long processId, Long threadId, Long reviewId, UpdateReviewCommand command
    );

    void deleteReview(Long userId, Long puppyId, Long processId, Long threadId, Long reviewId);

    GetEstimateDataResult getEstimateData(Long userId, Long puppyId, Long processId, Long threadId);

    GetReviewDetailResult getReviewDetail(Long userId, Long puppyId, Long processId, Long threadId, Long reviewId);

    GetDesignerReviewsResult getDesignerReviews(Long designerId);

    GetUserReviewsResult getUserReviews(Long userId);
}
