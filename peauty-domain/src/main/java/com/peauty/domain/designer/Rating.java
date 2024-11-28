package com.peauty.domain.designer;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private Long ratingId;
    private int totalScore;
    private float scissors;

    public void updateTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void updateScissors(float scissors) {
        this.scissors = scissors;
    }

}
