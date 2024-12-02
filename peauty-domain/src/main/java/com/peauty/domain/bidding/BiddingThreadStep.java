package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BiddingThreadStep {

    ESTIMATE_REQUEST("견적요청", 1),
    ESTIMATE_RESPONSE("견적응답", 2),
    RESERVED("예약성공", 3),
    COMPLETED("완료", 4);

    private final String description;
    private final int step;

    BiddingThreadStep(String description, int step) {
        this.description = description;
        this.step = step;
    }

    public boolean isEstimateRequest() {
        return this == ESTIMATE_REQUEST;
    }

    public boolean isEstimateResponse() {
        return this == ESTIMATE_RESPONSE;
    }

    public boolean isReserved() {
        return this == RESERVED;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isBefore(BiddingThreadStep other) {
        return this.step < other.step;
    }

    public boolean isAfter(BiddingThreadStep other) {
        return this.step > other.step;
    }

    public BiddingThreadStep getNextStep() {
        return fromStep(Math.min(this.step + 1, COMPLETED.step));
    }

    public boolean hasNotNextStep() {
        return this.step >= COMPLETED.step;
    }

    public static BiddingThreadStep fromDescription(String description) {
        return Arrays.stream(BiddingThreadStep.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_STEP_DESCRIPTION));
    }

    public static BiddingThreadStep fromStep(int step) {
        return Arrays.stream(BiddingThreadStep.values())
                .filter(it -> it.step == step)
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_STEP));
    }
}