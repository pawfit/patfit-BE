package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Disease {

    KENNEL_COUGH("Kennel Cough"),
    CANINE_INFLUENZA("Canine Influenza"),
    HEART_WORM("Heart Worm"),
    PARVOVIRUS("Parvovirus"),
    RABIES("Rabies"),
    EAR_INFECTION("Ear Infection"),
    CANINE_DISTEMPER("Canine Distemper"),
    FLEAS("Fleas"),
    PARASITES("Parasites");

    private final String description;

    Disease(String description) {
        this.description = description;
    }

    public static Disease from(String description) {
        return Arrays.stream(Disease.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXISTS_DISEASE));

    }
}
