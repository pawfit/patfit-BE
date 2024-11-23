package com.peauty.domain.puppy;

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
                .orElseThrow(() -> new IllegalArgumentException("잘못된 크기 설정입니다."));
    }

}
