package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

@Getter
public enum TotalGroomingBodyType {

    CLIPPING_3MM("클리핑 3mm"),
    CLIPPING_6MM("클리핑 6mm"),
    CLIPPING_9MM("클리핑 9mm"),
    SUMMER_SPORTING_3MM("썸머 스포팅 3mm"),
    SUMMER_SPORTING_6MM("썸머 스포팅 6mm"),
    SUMMER_SPORTING_9MM("썸머 스포팅 9mm"),
    SUMMER_PANTALONG_3MM("썸머 판타롱 3mm"),
    SUMMER_PANTALONG_6MM("썸머 판타롱 6mm"),
    SUMMER_PANTALONG_9MM("썸머 판타롱 9mm"),
    SUMMER_BELL_3MM("썸머 나팔 3mm"),
    SUMMER_BELL_6MM("썸머 나팔 6mm"),
    SUMMER_BELL_9MM("썸머 나팔 9mm");

    private final String description;

    TotalGroomingBodyType(String description) {
        this.description = description;
    }

    public static TotalGroomingBodyType from(String description) {
        for (TotalGroomingBodyType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new PeautyException(PeautyResponseCode.INVALID_BODY_TYPE);
    }
}