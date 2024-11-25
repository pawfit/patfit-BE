package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Sex {
    M("Male"),
    F("Female");

    private final String value;

    Sex(String value) {
        this.value = value;
    }

    public static Sex from(String value){
        return Arrays.stream(Sex.values())
                .filter(it -> it.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_SEX));
    }
}
