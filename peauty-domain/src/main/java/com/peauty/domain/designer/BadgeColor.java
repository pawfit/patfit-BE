package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BadgeColor {

    BLUE("blue"),
    GREEN("green"),
    BRONZE("bronze"),
    SILVER("silver"),
    GOLD("gold");

    private final String colorName;

    BadgeColor(String colorName) {
        this.colorName = colorName;
    }

    public static BadgeColor from(String badgeColor){
        return Arrays.stream(BadgeColor.values())
                .filter(it -> it.colorName.equalsIgnoreCase(badgeColor))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_COLOR_OPTION));

    }
}
