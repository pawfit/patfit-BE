package com.peauty.customer.business.review;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.customer.business.bidding.EstimatePort;
import com.peauty.customer.business.bidding.EstimateProposalPort;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.customer.business.review.dto.*;
import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewPort reviewPort;
    private final BiddingProcessPort biddingProcessPort;
    private final EstimatePort estimatePort;
    private final DesignerPort designerPort;
    private final WorkspacePort workspacePort;
    private final PuppyPort puppyPort;
    private final EstimateProposalPort estimateProposalPort;

    //TODO: 리뷰 중복 작성 금지 로직

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

        // 리뷰 생성, 저장
        Review newReview = command.toReview(thread.getSavedThreadId().value());
        Review savedReview = reviewPort.registerNewReview(newReview);

        // 리뷰 통계 업데이트
        Designer designer = designerPort.getDesignerById(thread.getDesignerId().value());
        workspacePort.registerReviewStats(designer.getDesignerId(), savedReview.getReviewRating());

        return RegisterReviewResult.from(savedReview);

    }

    @Override
    @Transactional
    public UpdateReviewResult updateReview(
            Long userId,
            Long reviewId,
            UpdateReviewCommand command
    ) {
        // 리뷰 ID로 리뷰를 조회
        Review existingReview = reviewPort.findReviewById(reviewId);

        // TODO: 리뷰와 관련된 스레드 ID가 요청 스레드 ID와 일치하지 않는 경우에 대한 검증 로직 필요
        // TODO: 프로세스가 요청한 userId와 puppyId에 연결되어 있는지 확인
        // 이전 평점 저장
        ReviewRating previousRating = existingReview.getReviewRating();

        // 리뷰 업데이트
        existingReview.updateReview(
                command.reviewRating(),
                command.contentDetail(),
                command.contentGenerals(),
                command.reviewImages()
        );

        // 업데이트된 리뷰 저장
        Review updatedReview = reviewPort.saveReview(existingReview);

        // 리뷰에 연결된 디자이너 ID 조회
        Long designerId = reviewPort.findDesignerIdByReviewId(reviewId);

        // 리뷰 통계 업데이트
        workspacePort.updateReviewStats(designerId, previousRating, updatedReview.getReviewRating());

        return UpdateReviewResult.from(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewPort.findReviewById(reviewId);
        // TODO: 해당 리뷰가 스레드와 일치하는지에 대한 검증 로직 필요.
        // TODO: 해당 리뷰에 맞는 유저와 강아지가 일치하는지에 대한 검증 로직 필요.

        Long designerId = reviewPort.findDesignerIdByReviewId(reviewId);
        ReviewRating deletedRating = review.getReviewRating();

        reviewPort.deleteReviewById(reviewId);
        workspacePort.deleteReviewStats(designerId, deletedRating);

    }

    @Override
    public GetReviewDetailResult getReviewDetail(Long userId, Long puppyId, Long processId, Long threadId, Long reviewId) {
        Review review = reviewPort.getReviewByIdAndBiddingThreadId(reviewId, threadId);
        // TODO: 조회 과정 수정하기
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        EstimateProposal proposal = estimateProposalPort.getProposalByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        Designer.DesignerProfile designerProfile = designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value());
        String groomingStyle = proposal.getSimpleGroomingStyle();
        Long desiredCost = proposal.getDesiredCost();
        Long biddingProcessId = process.getSavedProcessId().value();

        Puppy puppy = puppyPort.getPuppyByPuppyId(puppyId);
        String puppyName = puppy.getName();

        return GetReviewDetailResult.from(review, puppy, puppyName, desiredCost, groomingStyle, designerProfile, biddingProcessId);

    }


    @Override
    public GetEstimateDataResult getEstimateData(Long userId, Long puppyId, Long processId, Long threadId) {
        // 프로세스와 스레드 검증
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        if (!process.getPuppyId().value().equals(puppyId)) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_USER_OR_PUPPY);
        }

        // Estimate 가져오기
        Estimate.EstimateProfile estimateProfile = estimatePort.getEstimateByThreadId(threadId)
                .getProfile();

        // 디자이너,워크스페이스 가져오기
        Designer designer = designerPort.getAllDesignerDataByDesignerId(thread.getDesignerId().value());
        Workspace workspace = workspacePort.findByDesignerId(thread.getDesignerId().value());

        return GetEstimateDataResult.from(estimateProfile, designer, workspace);
    }

    @Override
    public GetDesignerReviewsResult getDesignerReviews(Long designerId) {
        List<Review> reviews = reviewPort.findReviewsByDesignerId(designerId);

        List<GetDesignerReviewsResult.ReviewDetails> reviewDetails = reviews.stream()
                .map(review -> new GetDesignerReviewsResult.ReviewDetails(
                        review.getId().map(Review.ID::value).orElse(null),
                        review.getReviewCreatedAt(),
                        review.getReviewerNickname(),
                        review.getGroomingStyle(),
                        review.getReviewRating().getValue(),
                        review.getReviewImages().stream().map(ReviewImage::getImageUrl).toList(),
                        review.getContentDetail()
                ))
                .toList();

        return new GetDesignerReviewsResult(designerId, reviewDetails);
    }

    @Override
    public GetUserReviewsResult getCustomerReviews(Long userId) {
        List<Review> reviews = reviewPort.findReviewsByCustomerId(userId);

        List<GetReviewDetailResult> reviewDetails = reviews.stream()
                .map(review -> {
                    BiddingProcess process = biddingProcessPort.getProcessByThreadId(review.getThreadId().value());
                    EstimateProposal proposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
                    Puppy puppy = puppyPort.getPuppyByPuppyId(process.getPuppyId().value());
                    Designer.DesignerProfile designerProfile = designerPort.getDesignerProfileByDesignerId(
                            process.getThread(new BiddingThread.ID(review.getThreadId().value())).getDesignerId().value()
                    );

                    return GetReviewDetailResult.from(
                            review,
                            puppy,
                            puppy.getName(),
                            proposal.getDesiredCost(),
                            proposal.getSimpleGroomingStyle(),
                            designerProfile,
                            process.getSavedProcessId().value()
                    );
                }).toList();

        return GetUserReviewsResult.from(userId, reviewDetails);
    }

}
