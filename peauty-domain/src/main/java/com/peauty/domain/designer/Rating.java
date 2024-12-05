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
    private Double totalScore;
    private Scissors scissors;

    public void updateTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
    public void updateScissors(Scissors scissors) {
        this.scissors = scissors;
    }

}
