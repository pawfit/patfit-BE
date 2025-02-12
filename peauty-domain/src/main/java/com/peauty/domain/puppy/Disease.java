package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Disease {

    NONE("없음"),
    ETC("기타"),
    PATELLA("슬개골"),
    EAR_INFECTION("외이염"),
    DERMATITIS("피부염"),
    EYE_DISEASE("눈질환"),
    HEART_DISEASE("심장병"),
    ARTHRITIS("관절염");

/*    KENNEL_COUGH("기침 감기"),
    CANINE_INFLUENZA("개 인플루엔자"),
    HEART_WORM("사상충"),
    PARVOVIRUS("홍반"),
    RABIES("광견병"),
    EAR_INFECTION("이염"),
    CANINE_DISTEMPER("홍역 바이러스"),
    FLEAS("벼룩"),
    PARASITES("기생충");*/

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
