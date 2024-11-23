package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PuppySize {

    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견");

    private final String description;

    PuppySize(String description) {
        this.description = description;
    }

    public static PuppySize from(String description) {
        return Arrays.stream(PuppySize.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_SIZE));

    }
}
