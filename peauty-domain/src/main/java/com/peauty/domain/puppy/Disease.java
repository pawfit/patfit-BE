package com.peauty.domain.puppy;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Disease {

    KennelCough("Kennel Cough"),
    CanineInfluenza("Canine Influenza"),
    HeartWorm("Heart Worm"),
    Parvovirus("Parvovirus"),
    Rabies("Rabies"),
    EarInfection("Ear Infection"),
    CanineDistemper("Canine Distemper"),
    Fleas("Fleas"),
    Parasites("Parasites");

    private final String description;

    Disease(String description) {
        this.description = description;
    }

    public static Disease from(String description) {
        return Arrays.stream(Disease.values())
                .filter(it -> it.description.equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("없는 병명입니다."));
    }

}
