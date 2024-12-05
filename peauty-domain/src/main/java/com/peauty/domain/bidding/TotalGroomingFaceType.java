package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

@Getter
public enum TotalGroomingFaceType {

    EGG("알머리"),
    BABY("베이비컷"),
    GODDESS("여신머리"),
    BEAR("곰돌이컷"),
    SEAL("물개컷"),
    LION("라이언컷"),
    CROWN("크라운컷"),
    TEDDY_BEAR("테디베어컷"),
    CANDY("캔디컷"),
    BROCCOLI("브로콜리컷"),
    HIGH_BAR("하이바"),
    POINTED_EARS("귀툭튀"),
    MONKEY("몽키컷"),
    BEDLINGTON("베들링턴컷"),
    POODLINGTON("푸들링턴컷");

    private final String description;

    TotalGroomingFaceType(String description) {
        this.description = description;
    }

    public static TotalGroomingFaceType from(String description) {
        for (TotalGroomingFaceType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new PeautyException(PeautyResponseCode.INVALID_FACE_TYPE);
    }
}