package com.peauty.domain.review;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContentGeneral {
    GOOD_SERVICE("서비스가 좋아요"),
    COME_AGAIN("다음에 또 오고 싶어요"),
    KIND("친절해요"),
    GOOD_COST("가성비 좋아요"),
    MYPICK("견적서대로 해줘요");

    private final String contentGeneralReview;

    ContentGeneral(String contentGeneralReview) {
        this.contentGeneralReview = contentGeneralReview;
    }

    public static ContentGeneral from(String contentGeneral) {
        return Arrays.stream(ContentGeneral.values())
                .filter(it -> it.contentGeneralReview.equalsIgnoreCase(contentGeneral))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_GENERAL_CONTENT));
    }

}
