package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Sex {
    M("수컷"),
    F("암컷");

    private final String description;

    Sex(String description) {
        this.description = description;
    }

    public static Sex from(String description){
        return Arrays.stream(Sex.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.WRONG_SEX));
    }
}
