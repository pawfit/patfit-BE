package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroomingBiddingProcess {

    @Getter private final ID id; // TODO Optional Getter 적용하기
    @Getter private final PuppyId puppyId;
    @Getter private GroomingBiddingProcessStatus status;
    @Getter private final GroomingBiddingProcessTimeInfo timeInfo;
    @Getter private final List<GroomingBiddingThread> threads;

    private GroomingBiddingProcess(
            GroomingBiddingProcess.ID id,
            PuppyId puppyId,
            GroomingBiddingProcessStatus status,
            GroomingBiddingProcessTimeInfo timeInfo,
            List<GroomingBiddingThread> threads
    ) {
        this.id = id;
        this.puppyId = puppyId;
        this.status = status;
        this.timeInfo = timeInfo;
        this.threads = new ArrayList<>(threads);
        threads.forEach(thread -> thread.registerProcessObserver(this));
    }

    public static GroomingBiddingProcess loadProcess(
            GroomingBiddingProcess.ID id,
            PuppyId puppyId,
            GroomingBiddingProcessStatus status,
            GroomingBiddingProcessTimeInfo timeInfo,
            List<GroomingBiddingThread> threads
    ) {
        return new GroomingBiddingProcess(id, puppyId, status, timeInfo, threads);
    }

    public static GroomingBiddingProcess createNewProcess(PuppyId puppyId) {
        return new GroomingBiddingProcess(
                null,
                puppyId,
                GroomingBiddingProcessStatus.RESERVED_YET,
                GroomingBiddingProcessTimeInfo.createNewTimeInfo(),
                new ArrayList<>()
        );
    }

    public static GroomingBiddingProcess createNewProcess(
            PuppyId puppyId,
            DesignerId designerId
    ) {
        GroomingBiddingProcess newProcess = createNewProcess(puppyId);
        newProcess.addNewThread(designerId);
        return newProcess;
    }

    public void addNewThread(DesignerId targetDesignerId) {
        validateProcessStatus();
        checkThreadAlreadyInProcess(targetDesignerId);
        GroomingBiddingThread newThread = GroomingBiddingThread.createNewThread(this.puppyId, targetDesignerId);
        newThread.registerProcessObserver(this);
        this.threads.add(newThread);
    }

    public void cancel() {
        validateProcessStatus();
        changeStatus(GroomingBiddingProcessStatus.CANCELED);
    }

    public void responseEstimateThread(GroomingBiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).responseEstimate();
    }

    public void reserveThread(GroomingBiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).reserve();
    }

    public void completeThread(GroomingBiddingThread.ID targetThreadId) {
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

    public void cancelThread(GroomingBiddingThread.ID targetThreadId) {
        getThreadForChangeState(targetThreadId).cancel();
    }

    public void cancelThread(DesignerId targetThreadDesignerId) {
        getThreadForChangeState(targetThreadDesignerId).cancel();
    }

    public GroomingBiddingThread getThread(GroomingBiddingThread.ID threadThreadId) {
        return threads.stream()
                .filter(thread -> thread.getId().value().equals(threadThreadId.value()))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    public GroomingBiddingThread getThread(DesignerId threadThreadDesignerId) {
        return threads.stream()
                .filter(thread -> thread.getDesignerId().value().equals(threadThreadDesignerId.value()))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    public void onReservedThreadCancel() {
        threads.forEach(GroomingBiddingThread::release);
        changeStatus(GroomingBiddingProcessStatus.RESERVED_YET);
    }

    public void onThreadReserved() {
        threads.forEach(GroomingBiddingThread::waiting);
        changeStatus(GroomingBiddingProcessStatus.RESERVED);
    }

    public void onThreadCompleted() {
        changeStatus(GroomingBiddingProcessStatus.COMPLETED);
    }

    private GroomingBiddingThread getThreadForChangeState(DesignerId targetThreadDesignerId) {
        validateProcessStatus();
        return getThread(targetThreadDesignerId);
    }

    private GroomingBiddingThread getThreadForChangeState(GroomingBiddingThread.ID targetThreaId) {
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

    private void changeStatus(GroomingBiddingProcessStatus status) {
        this.status = status;
        timeInfo.onStatusChange();
    }

    public Optional<GroomingBiddingProcess.ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
