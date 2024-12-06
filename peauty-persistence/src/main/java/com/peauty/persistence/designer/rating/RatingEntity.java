package com.peauty.persistence.designer.rating;

import com.peauty.domain.designer.Scissors;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RatingEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "total_score", nullable = false)
    private Double totalScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "scissors", nullable = false)
    private Scissors scissors;
}
