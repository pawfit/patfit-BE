package com.peauty.customer.business.review;

import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.customer.business.review.dto.RegisterReviewResult;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceFacade {

    private final ReviewService reviewService;

    @Transactional
    public RegisterReviewResult registerReviewWithRetry(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId,
            RegisterReviewCommand command
    ) throws InterruptedException {

        while (true) {
            try {
                return reviewService.registerReview(userId, puppyId, processId, threadId, command);
            } catch (ObjectOptimisticLockingFailureException e) {
                Thread.sleep(30);

            }
        }
    }
}