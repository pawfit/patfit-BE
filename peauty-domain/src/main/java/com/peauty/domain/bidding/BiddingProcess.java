package com.peauty.domain.bidding;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiddingProcess {

    private final ID id;
    @Getter private final PuppyId puppyId;
    @Getter private BiddingProcessStatus status;
    @Getter private final BiddingProcessTimeInfo timeInfo;
    @Getter private final List<BiddingThread> threads;

    private BiddingProcess(
            BiddingProcess.ID id,
            PuppyId puppyId,
            BiddingProcessStatus status,
            BiddingProcessTimeInfo timeInfo,
            List<BiddingThread> threads
    ) {
        this.id = id;
        this.puppyId = puppyId;
        this.status = status;
        this.timeInfo = timeInfo;
        this.threads = new ArrayList<>(threads);
        threads.forEach(thread -> thread.registerBelongingProcessObserver(this));
    }

    public static BiddingProcess loadProcess(
            BiddingProcess.ID id,
            PuppyId puppyId,
            BiddingProcessStatus status,
            BiddingProcessTimeInfo timeInfo,
            List<BiddingThread> threads
    ) {
        return new BiddingProcess(id, puppyId, status, timeInfo, threads);
    }

    public static BiddingProcess createNewProcess(PuppyId puppyId) {
        return new BiddingProcess(
                null,
                puppyId,
                BiddingProcessStatus.RESERVED_YET,
                BiddingProcessTimeInfo.createNewTimeInfo(),
                new ArrayList<>()
        );
    }

    public Optional<ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public ID getSavedProcessId() {
        return id;
    }

    public void addNewThread(DesignerId targetDesignerId) {
        validateProcessStatus();
        checkThreadAlreadyInProcess(targetDesignerId);
        BiddingThread newThread = BiddingThread.createNewThread(this.id, targetDesignerId);
        newThread.registerBelongingProcessObserver(this);
        this.threads.add(newThread);
    }

    public void cancel() {
        validateProcessStatus();
        changeStatus(BiddingProcessStatus.CANCELED);
    }

    public void responseEstimateThread(BiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).responseEstimate();
    }

    public void reserveThread(BiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).reserve();
    }

    public void completeThread(BiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).complete();
    }

    public void responseEstimateThread(DesignerId targetThreadDesignerId) {
        getThreadForChangeState(targetThreadDesignerId).responseEstimate();
    }

    public void reserveThread(DesignerId targetThreadDesignerId) {
        getThreadForChangeState(targetThreadDesignerId).reserve();
    }

    public void completeThread(DesignerId targetThreadDesignerId) {
        getThreadForChangeState(targetThreadDesignerId).complete();
    }

    public void cancelThread(BiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).cancel();
    }

    public void cancelThread(DesignerId targetThreadDesignerId) {
        getThreadForChangeState(targetThreadDesignerId).cancel();
    }

    public BiddingThread getThread(BiddingThread.ID targetThreadId) {
        return threads.stream()
                .filter(thread -> thread.getId()
                        .map(id -> id.value().equals(targetThreadId.value()))
                        .orElse(false))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    public BiddingThread getThread(DesignerId targetThreadDesignerId) {
        return threads.stream()
                .filter(thread -> thread.getDesignerId().value().equals(targetThreadDesignerId.value()))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    protected void onReservedThreadCancel() {
        threads.forEach(BiddingThread::release);
        changeStatus(BiddingProcessStatus.RESERVED_YET);
    }

    protected void onThreadReserved() {
        threads.forEach(BiddingThread::waiting);
        changeStatus(BiddingProcessStatus.RESERVED);
    }

    protected void onThreadCompleted() {
        changeStatus(BiddingProcessStatus.COMPLETED);
    }

    private BiddingThread getThreadForChangeState(DesignerId targetThreadDesignerId) {
        validateProcessStatus();
        return getThread(targetThreadDesignerId);
    }

    private BiddingThread getThreadForChangeState(BiddingThread.ID targetThreaId) {
        validateProcessStatus();
        return getThread(targetThreaId);
    }

    private void validateProcessStatus() {
        if (status.isCanceled()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);
        }
        if (status.isCompleted()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);
        }
    }

    private void checkThreadAlreadyInProcess(DesignerId designerId) {
        boolean isDuplicated = threads.stream()
                .anyMatch(thread -> thread.getDesignerId().value().equals(designerId.value()));
        if (isDuplicated) {
            throw new PeautyException(PeautyResponseCode.THREAD_ALREADY_IN_PROCESS);
        }
    }

    private void changeStatus(BiddingProcessStatus status) {
        this.status = status;
        timeInfo.onStatusChange();
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }

    public Profile getProfile() {
        return Profile.builder()
                .processId(id.value())
                .processStatus(status.getDescription())
                .processCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(
            Puppy.Profile puppyProfile,
            EstimateProposal.Profile estimateProposalProfile
    ) {
        return Profile.builder()
                .processId(id.value())
                .processStatus(status.getDescription())
                .puppy(puppyProfile)
                .estimateProposal(estimateProposalProfile)
                .processCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    public Profile getProfile(
            Puppy.Profile puppyProfile,
            EstimateProposal.Profile estimateProposalProfile,
            Designer.Profile designerProfile,
            Boolean isThreadReviewed // TODO 리뷰는 스레드에 달리는 것이 더 좋아보이긴 함..
    ) {
        BiddingThread thread = getThread(new DesignerId(designerProfile.designerId()));
        return Profile.builder()
                .processId(id.value())
                .processStatus(status.getDescription())
                .isThreadReviewed(isThreadReviewed)
                .puppy(puppyProfile)
                .estimateProposal(estimateProposalProfile)
                .thread(thread.getProfile(designerProfile))
                .processCreatedAt(timeInfo.getCreatedAt())
                .build();
    }

    @Builder
    public record Profile(
            Long processId,
            String processStatus,
            LocalDateTime processCreatedAt,
            Puppy.Profile puppy,
            EstimateProposal.Profile estimateProposal,
            BiddingThread.Profile thread,
            Boolean isThreadReviewed
    ) {
    }
}
