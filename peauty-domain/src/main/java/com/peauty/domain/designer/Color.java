package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;

import java.util.Arrays;

public enum Color {

    BLUE("블루"),
    GREEN("그린"),
    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드");

    private final String badgeColor;

    Color(String badgeColor) {
        this.badgeColor = badgeColor;
    }
    public static Color from(String color){
        return Arrays.stream(Color.values())
                .filter(it -> it.badgeColor.equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_COLOR_OPTION));
    }


}
