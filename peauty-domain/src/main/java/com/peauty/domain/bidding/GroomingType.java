package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GroomingType {
    TOTAL("전체미용"),
    CLEAN("위생미용");

    private final String description;

    GroomingType(String description) {
        this.description = description;
    }

    public static GroomingType from(String type) {
        return Arrays.stream(values())
                .filter(groomingType -> groomingType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_GROOMING_TYPE));
    }

    public boolean isTotalGrooming() {
        return this == TOTAL;
    }

    public boolean isCleanGrooming() {
        return this == CLEAN;
    }
}
