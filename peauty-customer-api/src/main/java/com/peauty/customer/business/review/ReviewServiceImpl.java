package com.peauty.customer.business.review;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.customer.business.review.dto.RegisterReviewResult;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewPort reviewPort;
    private final BiddingProcessPort biddingProcessPort;

    @Override
    @Transactional
    public RegisterReviewResult registerReview(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId,
            RegisterReviewCommand command
    ){

        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));

        // 스레드 상태가 COMPLETED인지 확인
        if (!thread.getStep().isCompleted()) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER);
        }

        // 리뷰 생성 및 저장
        Review newReview = command.toReview(thread.getSavedThreadId().value());
        Review savedReview = reviewPort.save(newReview);

        return RegisterReviewResult.from(savedReview);

    }

}
