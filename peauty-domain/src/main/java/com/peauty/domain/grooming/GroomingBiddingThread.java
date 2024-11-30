package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroomingBiddingThread {

    @Getter private final GroomingBiddingThreadId id;
    @Getter private final DesignerId designerId;
    @Getter private GroomingBiddingThreadStep step;
    private GroomingBiddingThreadStatus status;
    private GroomingBiddingThreadTimeInfo timeInfo;
    private GroomingBiddingProcess processObserver;

    public static GroomingBiddingThread withoutId(
            DesignerId designerId,
            GroomingBiddingThreadStep step,
            GroomingBiddingThreadStatus status,
            GroomingBiddingThreadTimeInfo timeInfo
    ) {
        return new GroomingBiddingThread(null, designerId, step, status, timeInfo, null);
    }

    public static GroomingBiddingThread withId(
            GroomingBiddingThreadId id,
            DesignerId designerId,
            GroomingBiddingThreadStep step,
            GroomingBiddingThreadStatus status,
            GroomingBiddingThreadTimeInfo timeInfo
    ) {
        return new GroomingBiddingThread(id, designerId, step, status, timeInfo, null);
    }

    public void registerProcessObserver(GroomingBiddingProcess process) {
        this.processObserver = process;
    }

    public void progressStep() {
        validateProgressStep();
        changeToNextStep();
        if (step == GroomingBiddingThreadStep.RESERVED) {
            processObserver.onThreadReserved();
        } else if (step == GroomingBiddingThreadStep.COMPLETED) {
            processObserver.onThreadCompleted();
        }
    }

    public void cancel() {
        validateCancellation();
        if (isReserved()) {
            // TODO 결제 환불
            cancelReservedThread();
            return;
        }
        cancelThread();
    }

    private void validateCancellation() {
        if (isCanceled()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_THREAD);
        }
        if (isCompleted()) {
            throw new PeautyException(PeautyResponseCode.CANNOT_CANCEL_COMPLETED_THREAD);
        }
    }

    private void validateProgressStep() {
        if (status == GroomingBiddingThreadStatus.CANCELED) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_CANCELED_THREAD_STEP);
        }
        if (status == GroomingBiddingThreadStatus.WAITING) {
            throw new PeautyException(PeautyResponseCode.CANNOT_PROGRESS_WAITING_THREAD_STEP);
        }
        if (step.hasNotNextStep()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_THREAD);
        }
    }

    private boolean isCanceled() {
        return status == GroomingBiddingThreadStatus.CANCELED;
    }

    private boolean isCompleted() {
        return step == GroomingBiddingThreadStep.COMPLETED;
    }

    private boolean isReserved() {
        return step == GroomingBiddingThreadStep.RESERVED;
    }

    private void cancelReservedThread() {
        changeStatus(GroomingBiddingThreadStatus.CANCELED);
        processObserver.onReservedThreadCancel();
    }

    private void cancelThread() {
        changeStatus(GroomingBiddingThreadStatus.CANCELED);
    }

    public void waiting() {
        if (status == GroomingBiddingThreadStatus.ONGOING & step.getStep() < GroomingBiddingThreadStep.RESERVED.getStep()) {
            changeStatus(GroomingBiddingThreadStatus.WAITING);
        }
    }

    public void release() {
        if (status == GroomingBiddingThreadStatus.WAITING) {
            changeStatus(GroomingBiddingThreadStatus.ONGOING);
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

    public record GroomingBiddingThreadId(Long value) {
    }

    public record DesignerId(Long value) {
    }
}
