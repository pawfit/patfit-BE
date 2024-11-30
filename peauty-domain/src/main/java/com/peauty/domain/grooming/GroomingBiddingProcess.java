package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroomingBiddingProcess {

    private final GroomingBiddingProcessId id;
    private final CustomerId customerId;
    private GroomingBiddingProcessStatus status;
    private GroomingBiddingProcessTimeInfo timeInfo;
    private final List<GroomingBiddingThread> threads;

    private GroomingBiddingProcess(
            GroomingBiddingProcessId id,
            CustomerId customerId,
            GroomingBiddingProcessStatus status,
            List<GroomingBiddingThread> threads) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.threads = new ArrayList<>(threads);
        threads.forEach(thread -> thread.registerProcessObserver(this));
    }

    public static GroomingBiddingProcess withoutId(
            CustomerId customerId,
            GroomingBiddingProcessStatus status,
            List<GroomingBiddingThread> threads
    ) {
        return new GroomingBiddingProcess(null, customerId, status, threads);
    }

    public static GroomingBiddingProcess withId(
            GroomingBiddingProcessId id,
            CustomerId customerId,
            GroomingBiddingProcessStatus status,
            List<GroomingBiddingThread> threads
    ) {
        return new GroomingBiddingProcess(id, customerId, status, threads);
    }

    public void addThread(GroomingBiddingThread thread) {
        validateProcessStatus();
        checkDuplicatedThread(thread.getId().value());
        thread.registerProcessObserver(this);
        this.threads.add(thread);
    }

    public void progressThreadStep(Long threadId) {
        validateProcessStatus();
        GroomingBiddingThread thread = getThreadById(threadId);
        thread.progressStep();
    }

    public void cancelThread(Long threadId) {
        validateProcessStatus();
        GroomingBiddingThread thread = getThreadById(threadId);
        thread.cancel();
    }

    public void cancelProcess() {
        validateProcessStatus();
        changeStatus(GroomingBiddingProcessStatus.CANCELED);
    }

    public Optional<GroomingBiddingProcessId> getId(){
        return Optional.ofNullable(this.id);
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

    private GroomingBiddingThread getThreadById(Long threadId) {
        return threads.stream()
                .filter(thread -> thread.getId().value().equals(threadId))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
    }

    private void validateProcessStatus() {
        if (status == GroomingBiddingProcessStatus.CANCELED) {
            throw new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);
        }
        if (status == GroomingBiddingProcessStatus.COMPLETED) {
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);
        }
    }

    private void checkDuplicatedThread(Long threadId) {
        boolean isDuplicated = threads.stream()
                .anyMatch(thread -> thread.getId().value().equals(threadId));
        if (isDuplicated) {
            throw new PeautyException(PeautyResponseCode.THREAD_ALREADY_IN_PROCESS);
        }
    }

    private void changeStatus(GroomingBiddingProcessStatus targetStatus) {
        status = targetStatus;
        timeInfo.onStatusChange();
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public record GroomingBiddingProcessId(Long value) {
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public record CustomerId(Long value) {
    }
}
