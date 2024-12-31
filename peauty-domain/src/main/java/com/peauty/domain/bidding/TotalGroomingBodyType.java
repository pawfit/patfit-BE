package com.peauty.domain.bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

@Getter
public enum TotalGroomingBodyType {

    CLIPPING_3MM("클리핑컷 3mm"),
    CLIPPING_6MM("클리핑컷 6mm"),
    CLIPPING_9MM("클리핑컷 9mm"),
    SPORTING_3MM("스포팅컷 3mm"),
    SPORTING_6MM("스포팅컷 6mm"),
    SPORTING_9MM("스포팅컷 9mm"),
    PANTALONG_3MM("판타롱컷 3mm"),
    PANTALONG_6MM("판타롱컷 6mm"),
    PANTALONG_9MM("판타롱컷 9mm"),
    BELL_3MM("나팔컷 3mm"),
    BELL_6MM("나팔컷 6mm"),
    BELL_9MM("나팔컷 9mm"),
    SCISSORS("가위컷");

    private final String description;

    TotalGroomingBodyType(String description) {
        this.description = description;
    }

    public String getBaseStyleName() {
        if (this == SCISSORS) {
            return description;
        }
        return description.replaceAll("\\s+\\d+mm$", "");
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