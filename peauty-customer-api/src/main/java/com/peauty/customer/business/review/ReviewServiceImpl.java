package com.peauty.customer.business.review;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.customer.business.review.dto.RegisterReviewCommand;
import com.peauty.customer.business.review.dto.RegisterReviewResult;
import com.peauty.customer.business.review.dto.UpdateReviewCommand;
import com.peauty.customer.business.review.dto.UpdateReviewResult;
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
            throw new PeautyException(PeautyResponseCode.CANNOT_REVIEW_INCOMPLETED_THREAD);
        }

        // 리뷰 생성 및 저장
        Review newReview = command.toReview(thread.getSavedThreadId().value());
        Review savedReview = reviewPort.registerNewReview(newReview);

        return RegisterReviewResult.from(savedReview);

    }

    @Override
    @Transactional
    public UpdateReviewResult updateReview(
            Long userId,
            Long puppyId,
            Long threadId,
            Long processId,
            Long reviewId,
            UpdateReviewCommand command
    ) {
        // 1. 리뷰 ID로 리뷰를 조회
        Review existingReview = reviewPort.findReviewById(reviewId);

        // 2. 리뷰와 관련된 스레드 ID가 요청 스레드 ID와 일치하지 않는 경우
        if (!existingReview.getThreadId().value().equals(threadId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_THREAD_MISMATCH);
        }

        // 3. 스레드가 유효한 사용자의 프로세스에 포함되었는지 확인
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));

        // 4. 프로세스가 요청한 userId와 puppyId에 연결되어 있는지 확인
        if (!process.getPuppyId().value().equals(puppyId) || !thread.getProcessId().value().equals(processId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER_OR_PUPPY);
        }
/*        existingReview.updateRatingService(command.reviewRating());
        existingReview.updateContentDetail(command.contentDetail());
        existingReview.updateContentGeneral(command.contentGeneral());
        existingReview.updateReviewImageUrl(command.reviewImages());*/
        existingReview.updateReview(
                command.reviewRating(),
                command.contentDetail(),
                command.contentGeneral(),
                command.reviewImages()
        );

        Review updatedReview = reviewPort.saveReview(existingReview);

        return UpdateReviewResult.from(updatedReview);
    }


}
