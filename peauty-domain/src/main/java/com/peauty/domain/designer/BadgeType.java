package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BadgeType {

    GENERAL("일반"),
    SCISSORS("시저");

    private final String typeName;

    BadgeType(String typeName){ this.typeName = typeName; }

    public static BadgeType from(String badgeTypeName){
        return Arrays.stream(BadgeType.values())
                .filter(it -> it.typeName.equalsIgnoreCase(badgeTypeName))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_COLOR_OPTION));
        // TODO: 풀 받고 리스폰스 코드 변경 예정
    }

}
