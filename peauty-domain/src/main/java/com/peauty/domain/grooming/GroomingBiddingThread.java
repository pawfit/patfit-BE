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
                GroomingBiddingThreadStatus.ONGOING,
                GroomingBiddingThreadTimeInfo.createNewTimeInfo(),
                null
        );
    }

    public void registerProcessObserver(GroomingBiddingProcess process) {
        this.processObserver = process;
    }

    public void progressStep() {
        validateProgressingStep();
        changeToNextStep();
        if (step.isReserved()) {
            processObserver.onThreadReserved();
        } else if (step.isCompleted()) {
            processObserver.onThreadCompleted();
        }
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
        if (status.isOngoing() & step.isBefore(GroomingBiddingThreadStep.RESERVED)) {
            changeStatus(GroomingBiddingThreadStatus.WAITING);
        }
    }

    public void release() {
        if (status.isWaiting()) {
            changeStatus(GroomingBiddingThreadStatus.ONGOING);
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

    private void validateProgressingStep() {
        if (status.isCanceled()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_CANCELED_THREAD_STEP);
        }
        if (status.isWaiting()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_WAITING_THREAD_STEP);
        }
        if (step.hasNotNextStep()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_THREAD);
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
