package com.peauty.domain.grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GroomingBiddingProcessStatus {

    RESERVED_YET("예약 전"),
    RESERVED("예약 성공"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String description;

    GroomingBiddingProcessStatus(String description) {
        this.description = description;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isReserved() {
        return this == RESERVED;
    }

    public boolean isReservedYet() {
        return this == RESERVED_YET;
    }

    public static GroomingBiddingProcessStatus from(String description) {
        return Arrays.stream(GroomingBiddingProcessStatus.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_BIDDING_PROCESS_EXCEPTION_STATUS));
    }
}
