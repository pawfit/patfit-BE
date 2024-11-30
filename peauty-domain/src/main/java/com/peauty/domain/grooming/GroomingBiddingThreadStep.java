package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GroomingBiddingThreadStep {

    ESTIMATE_REQUEST("견적요청", 1),
    ESTIMATE_RESPONSE("견적응답", 2),
    RESERVED("예약성공", 3),
    COMPLETED("완료", 4);

    private final String description;
    private final int step;

    GroomingBiddingThreadStep(String description, int step) {
        this.description = description;
        this.step = step;
    }

    public static GroomingBiddingThreadStep fromDescription(String description) {
        return Arrays.stream(GroomingBiddingThreadStep.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_STEP_DESCRIPTION));
    }

    public static GroomingBiddingThreadStep fromStep(int step) {
        return Arrays.stream(GroomingBiddingThreadStep.values())
                .filter(it -> it.step == step)
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_STEP));
    }

    public GroomingBiddingThreadStep getNextStep() {
        return fromStep(Math.min(this.step + 1, COMPLETED.step));
    }

    public boolean hasNotNextStep() {
        return this.step >= COMPLETED.step;
    }
}