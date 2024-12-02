package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiddingProcess {

    @Getter private final ID id; // TODO Optional Getter 적용하기
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
        threads.forEach(thread -> thread.registerProcessObserver(this));
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

    public static BiddingProcess createNewProcess(
            PuppyId puppyId,
            DesignerId designerId
    ) {
        BiddingProcess newProcess = createNewProcess(puppyId);
        newProcess.addNewThread(designerId);
        return newProcess;
    }

    public void addNewThread(DesignerId targetDesignerId) {
        validateProcessStatus();
        checkThreadAlreadyInProcess(targetDesignerId);
        BiddingThread newThread = BiddingThread.createNewThread(this.puppyId, targetDesignerId);
        newThread.registerProcessObserver(this);
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

    public BiddingThread getThread(BiddingThread.ID threadThreadId) {
        return threads.stream()
                .filter(thread -> thread.getId().value().equals(threadThreadId.value()))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    public BiddingThread getThread(DesignerId threadThreadDesignerId) {
        return threads.stream()
                .filter(thread -> thread.getDesignerId().value().equals(threadThreadDesignerId.value()))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    public void onReservedThreadCancel() {
        threads.forEach(BiddingThread::release);
        changeStatus(BiddingProcessStatus.RESERVED_YET);
    }

    public void onThreadReserved() {
        threads.forEach(BiddingThread::waiting);
        changeStatus(BiddingProcessStatus.RESERVED);
    }

    public void onThreadCompleted() {
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

    public Optional<BiddingProcess.ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
