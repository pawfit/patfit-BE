package com.peauty.customer.business.review;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.customer.business.bidding.EstimatePort;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.review.dto.*;
import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
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
    private final EstimatePort estimatePort;
    private final DesignerPort designerPort;
    private final WorkspacePort workspacePort;

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
        // 리뷰 ID로 리뷰를 조회
        Review existingReview = reviewPort.findReviewById(reviewId);
        // 리뷰와 관련된 스레드 ID가 요청 스레드 ID와 일치하지 않는 경우
        if (!existingReview.getThreadId().value().equals(threadId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_THREAD_MISMATCH);
        }

        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));

        // 프로세스가 요청한 userId와 puppyId에 연결되어 있는지 확인
        if (!process.getPuppyId().value().equals(puppyId) || !thread.getProcessId().value().equals(processId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER_OR_PUPPY);
        }

        existingReview.updateReview(
                command.reviewRating(),
                command.contentDetail(),
                command.contentGeneral(),
                command.reviewImages()
        );

        Review updatedReview = reviewPort.saveReview(existingReview);

        return UpdateReviewResult.from(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long userId, Long puppyId, Long threadId, Long processId, Long reviewId) {
        Review review = reviewPort.findReviewById(reviewId);
        if (!review.getThreadId().value().equals(threadId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_THREAD_MISMATCH);
        }
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        if (!process.getPuppyId().value().equals(puppyId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER_OR_PUPPY);
        }

        reviewPort.deleteReviewById(reviewId);
    }

    @Override
    public GetEstimateDataResult getEstimateData(Long userId, Long puppyId, Long threadId, Long processId) {
        // 프로세스와 스레드 검증
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        if (!process.getPuppyId().value().equals(puppyId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER_OR_PUPPY);
        }

        // Estimate 가져오기
        Estimate.Profile estimateProfile = estimatePort.getEstimateByThreadId(threadId)
                .getProfile();

        // 디자이너,워크스페이스 가져오기
        Designer designer = designerPort.getAllDesignerDataByDesignerId(thread.getDesignerId().value());
        Workspace workspace = workspacePort.getByDesignerId(thread.getDesignerId().value());

        return GetEstimateDataResult.from(estimateProfile, designer, workspace);
    }




}
