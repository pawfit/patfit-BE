package com.peauty.domain.bidding;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BiddingThread {

    private final ID id;
    @Getter private final BiddingProcess.ID processId;
    @Getter private final DesignerId designerId;
    @Getter private BiddingThreadStep step;
    @Getter private BiddingThreadStatus status;
    @Getter private BiddingThreadTimeInfo timeInfo;
    private BiddingProcess belongingProcessObserver;

    public static BiddingThread loadThread(
            ID id,
            BiddingProcess.ID processId,
            DesignerId designerId,
            BiddingThreadStep step,
            BiddingThreadStatus status,
            BiddingThreadTimeInfo timeInfo
    ) {
        return new BiddingThread(id, processId, designerId, step, status, timeInfo, null);
    }

    protected static BiddingThread createNewThread(BiddingProcess.ID processId, DesignerId designerId) {
        return new BiddingThread(
                null,
                processId,
                designerId,
                BiddingThreadStep.ESTIMATE_REQUEST,
                BiddingThreadStatus.NORMAL,
                BiddingThreadTimeInfo.createNewTimeInfo(),
                null
        );
    }

    public Optional<ID> getId() {
        return Optional.ofNullable(id);
    }

    public ID getSavedThreadId() {
        return id;
    }

    protected void registerBelongingProcessObserver(BiddingProcess belongingProcess) {

        // 새로운 프로세스-스레드 생성 케이스
        if (belongingProcess.getId().isEmpty() && this.processId == null) {
            this.belongingProcessObserver = belongingProcess;
            return;
        }

        // 저장된 프로세스-스레드 연결 케이스
        Long processId = belongingProcess.getId()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.PROCESS_NOT_REGISTERED))
                .value();
        if (!this.processId.isSameId(processId)) {
            throw new PeautyException(PeautyResponseCode.ONLY_BELONGING_PROCESS_CAN_BE_OBSERVER);
        }
        this.belongingProcessObserver = belongingProcess;
    }

    protected void responseEstimate() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.ESTIMATE_RESPONSE);
        changeToNextStep();
    }

    protected void reserve() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.RESERVED);
        changeToNextStep();
        validateObserverRegistered();
        belongingProcessObserver.onThreadReserved();
    }

    protected void complete() {
        validateStatusForStepProgressing();
        validateProgressTo(BiddingThreadStep.COMPLETED);
        changeToNextStep();
        validateObserverRegistered();
        belongingProcessObserver.onThreadCompleted();
    }

    protected void cancel() {
        validateCancellation();
        if (step.isReserved()) {
            // TODO 결제 환불
            changeStatus(BiddingThreadStatus.CANCELED);
            validateObserverRegistered();
            belongingProcessObserver.onReservedThreadCancel();
            return;
        }
        changeStatus(BiddingThreadStatus.CANCELED);
    }

    protected void waiting() {
        if (status.isNormal() & step.isBefore(BiddingThreadStep.RESERVED)) {
            changeStatus(BiddingThreadStatus.WAITING);
        }
    }

    protected void release() {
        if (status.isWaiting()) {
            changeStatus(BiddingThreadStatus.NORMAL);
        }
    }

    private void validateObserverRegistered() {
        if (belongingProcessObserver == null) {
            throw new PeautyException(PeautyResponseCode.PROCESS_OBSERVER_NOT_REGISTERED);
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

    public Profile getProfile() {
        return Profile.builder()
                .processId(processId.value())
                .threadId(id.value())
                .threadStep(step.getStep())
                .threadStatus(status.getDescription())
                .threadCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(Designer.Profile designerProfile) {
        return Profile.builder()
                .processId(processId.value())
                .threadId(id.value())
                .threadStep(step.getStep())
                .threadStatus(status.getDescription())
                .designer(designerProfile)
                .threadCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(Estimate.Profile estimateProfile) {
        return Profile.builder()
                .processId(processId.value())
                .threadId(id.value())
                .threadStep(step.getStep())
                .threadStatus(status.getDescription())
                .estimate(estimateProfile)
                .threadCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(Designer.Profile designerProfile, Estimate.Profile estimateProfile) {
        return Profile.builder()
                .processId(processId.value())
                .threadId(id.value())
                .threadStep(step.getStep())
                .threadStatus(status.getDescription())
                .designer(designerProfile)
                .estimate(estimateProfile)
                .threadCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(Designer.Profile designerProfile, Estimate.Profile estimateProfile, Boolean isReviewed) {
        return Profile.builder()
                .processId(processId.value())
                .threadId(id.value())
                .threadStep(step.getStep())
                .threadStatus(status.getDescription())
                .isReviewed(isReviewed)
                .designer(designerProfile)
                .estimate(estimateProfile)
                .threadCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    @Builder
    public record Profile(
            Long processId,
            Long threadId,
            Integer threadStep,
            String threadStatus,
            Boolean isReviewed,
            LocalDateTime threadCreatedAt,
            Designer.Profile designer,
            Estimate.Profile estimate
    ) {
    }
}