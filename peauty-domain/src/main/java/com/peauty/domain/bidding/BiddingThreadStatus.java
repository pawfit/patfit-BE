package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BiddingThreadStatus {

    NORMAL("정상"),
    CANCELED("취소"),
    WAITING("대기");

    private final String description;

    BiddingThreadStatus(String description) {
        this.description = description;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

    public boolean isNormal() {
        return this == NORMAL;
    }

    public boolean isWaiting() {
        return this == WAITING;
    }

    public static BiddingThreadStatus from(String description) {
        return Arrays.stream(BiddingThreadStatus.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_THREAD_EXCEPTION_STATUS));
    }
}
