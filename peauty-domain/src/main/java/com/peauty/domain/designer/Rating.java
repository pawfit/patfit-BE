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
    private Scissor scissor;
}
