package com.peauty.domain.puppy;

public enum PuppySize {

    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견");

    private final String description;

    PuppySize(String description) {
        this.description = description;
    }



}
