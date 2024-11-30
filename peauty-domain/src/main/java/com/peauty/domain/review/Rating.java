package com.peauty.domain.review;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Rating {

    ZERO(0.0),
    ZERO_POINT_FIVE(0.5),
    ONE(1.0),
    ONE_POINT_FIVE(1.5),
    TWO(2.0),
    TWO_POINT_FIVE(2.5),
    THREE(3.0),
    THREE_POINT_FIVE(3.5),
    FOUR(4.0),
    FOUR_POINT_FIVE(4.5),
    FIVE(5.0);
// float 사용할 시 숫자 뒤 f 붙여야하기 때문에 double로 타입 확정
    private final double value;

    Rating(double value){
        this.value = value;
    }

    public static Rating from(double value){
        return Arrays.stream(Rating.values())
                .filter(it -> it.value == value) // double 값 비교
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_RATING));
    }


}
