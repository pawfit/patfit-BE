package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroomingBiddingThread {

    @Getter private final ID id; // TODO Optional Getter 적용하기
    @Getter private final PuppyId puppyId;
    @Getter private final DesignerId designerId;
    @Getter private GroomingBiddingThreadStep step;
    @Getter private GroomingBiddingThreadStatus status;
    @Getter private GroomingBiddingThreadTimeInfo timeInfo;
    private GroomingBiddingProcess processObserver;

    public static GroomingBiddingThread loadThread(
            ID id,
            PuppyId puppyId,
            DesignerId designerId,
            GroomingBiddingThreadStep step,
            GroomingBiddingThreadStatus status,
            GroomingBiddingThreadTimeInfo timeInfo
    ) {
        return new GroomingBiddingThread(id, puppyId, designerId, step, status, timeInfo, null);
    }

    public static GroomingBiddingThread createNewThread(PuppyId puppyId, DesignerId designerId) {
        return new GroomingBiddingThread(
                null,
                puppyId,
                designerId,
                GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                GroomingBiddingThreadStatus.NORMAL,
                GroomingBiddingThreadTimeInfo.createNewTimeInfo(),
                null
        );
    }

    public void registerProcessObserver(GroomingBiddingProcess process) {
        this.processObserver = process;
    }

    public void responseEstimate() {
        validateStatusForStepProgressing();
        validateProgressTo(GroomingBiddingThreadStep.ESTIMATE_RESPONSE);
        changeToNextStep();
    }

    public void reserve() {
        validateStatusForStepProgressing();
        validateProgressTo(GroomingBiddingThreadStep.RESERVED);
        changeToNextStep();
        processObserver.onThreadReserved();
    }

    public void complete() {
        validateStatusForStepProgressing();
        validateProgressTo(GroomingBiddingThreadStep.COMPLETED);
        changeToNextStep();
        processObserver.onThreadCompleted();
    }

    public void cancel() {
        validateCancellation();
        if (step.isReserved()) {
            // TODO 결제 환불
            changeStatus(GroomingBiddingThreadStatus.CANCELED);
            processObserver.onReservedThreadCancel();
            return;
        }
        changeStatus(GroomingBiddingThreadStatus.CANCELED);
    }

    public void waiting() {
        if (status.isNormal() & step.isBefore(GroomingBiddingThreadStep.RESERVED)) {
            changeStatus(GroomingBiddingThreadStatus.WAITING);
        }
    }

    public void release() {
        if (status.isWaiting()) {
            changeStatus(GroomingBiddingThreadStatus.NORMAL);
        }
    }

    private void validateProgressTo(GroomingBiddingThreadStep nextStep) {
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

    private void changeStatus(GroomingBiddingThreadStatus targetStatus) {
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