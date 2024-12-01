package com.peauty.persistence.designer;

import com.peauty.domain.designer.Scissor;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private DesignerEntity designer;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "scissors")
    @Enumerated(EnumType.STRING)
    private Scissor scissors;
}
