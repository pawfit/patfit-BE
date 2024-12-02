package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BiddingThread {

    @Getter private final ID id; // TODO Optional Getter 적용하기
    @Getter private final PuppyId puppyId;
    @Getter private final DesignerId designerId;
    @Getter private BiddingThreadStep step;
    @Getter private BiddingThreadStatus status;
    @Getter private BiddingThreadTimeInfo timeInfo;
    private BiddingProcess processObserver;

    public static BiddingThread loadThread(
            ID id,
            PuppyId puppyId,
            DesignerId designerId,
            BiddingThreadStep step,
            BiddingThreadStatus status,
            BiddingThreadTimeInfo timeInfo
    ) {
        return new BiddingThread(id, puppyId, designerId, step, status, timeInfo, null);
    }

    public static BiddingThread createNewThread(PuppyId puppyId, DesignerId designerId) {
        return new BiddingThread(
                null,
                puppyId,
                designerId,
                BiddingThreadStep.ESTIMATE_REQUEST,
                BiddingThreadStatus.NORMAL,
                BiddingThreadTimeInfo.createNewTimeInfo(),
                null
        );
    }

    public void registerProcessObserver(BiddingProcess process) {
        this.processObserver = process;
    }

    public void responseEstimate() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.ESTIMATE_RESPONSE);
        changeToNextStep();
    }

    public void reserve() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.RESERVED);
        changeToNextStep();
        processObserver.onThreadReserved();
    }

    public void complete() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.COMPLETED);
        changeToNextStep();
        processObserver.onThreadCompleted();
    }

    public void cancel() {
        validateCancellation();
        if (step.isReserved()) {
            // TODO 결제 환불
            changeStatus(BiddingThreadStatus.CANCELED);
            processObserver.onReservedThreadCancel();
            return;
        }
        changeStatus(BiddingThreadStatus.CANCELED);
    }

    public void waiting() {
        if (status.isNormal() & step.isBefore(BiddingThreadStep.RESERVED)) {
            changeStatus(BiddingThreadStatus.WAITING);
        }
    }

    public void release() {
        if (status.isWaiting()) {
            changeStatus(BiddingThreadStatus.NORMAL);
        }
    }

    private void validateProgressTo(BiddingThreadStep nextStep) {
        if (step.isCompleted()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_THREAD);
        }
        if (nextStep.getStep() - step.getStep() != 1) {
            throw new PeautyException(PeautyResponseCode.INVALID_STEP_PROGRESSING);
        }
    }

    private void validateCancellation() {
        if (status.isCanceled()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_THREAD);
        }
        if (step.isCompleted()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_CANCEL_COMPLETED_THREAD);
        }
    }

    private void validateStatusForStepProgressing() {
        if (status.isCanceled()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_CANCELED_THREAD_STEP);
        }
        if (status.isWaiting()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_WAITING_THREAD_STEP);
        }
    }

    private void changeStatus(BiddingThreadStatus targetStatus) {
        status = targetStatus;
        timeInfo.onStatusChange();
    }

    private void changeToNextStep() {
        step = step.getNextStep();
        timeInfo.onStepChange();
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}