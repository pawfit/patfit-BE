package com.peauty.persistence.designer;

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

    @Column(name = "scissors")
    @Enumerated(EnumType.STRING)
    private Scissors scissors;
}
