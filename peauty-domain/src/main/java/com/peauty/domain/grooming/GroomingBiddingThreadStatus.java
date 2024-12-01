package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GroomingBiddingThreadStatus {

    ONGOING("진행 중"),
    CANCELED("취소"),
    WAITING("대기");

    private final String description;

    GroomingBiddingThreadStatus(String description) {
        this.description = description;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

    public boolean isOngoing() {
        return this == ONGOING;
    }

    public boolean isWaiting() {
        return this == WAITING;
    }

    public static GroomingBiddingThreadStatus from(String description) {
        return Arrays.stream(GroomingBiddingThreadStatus.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_EXCEPTION_STATUS));
    }
}
