package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Scissors {

    NONE("미획득"),
    GOLD("금"),
    SILVER("은"),
    BRONZE("동");

    private final String scissorsRank;

    Scissors(String scissorsRank) {
        this.scissorsRank = scissorsRank;
    }
    public static Scissors from(String scissors) {
        return Arrays.stream(Scissors.values())
                .filter(it -> it.scissorsRank.equalsIgnoreCase(scissors))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_SCISSORS_RANK_OPTION));
    }


}
