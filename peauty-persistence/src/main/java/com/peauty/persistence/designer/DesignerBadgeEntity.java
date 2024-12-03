package com.peauty.persistence.designer;

import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designer_badge")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerBadgeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "designer_id")
    private Long designerId;

    @JoinColumn(name = "badge_id")
    private Long badgeId;

    @Column(name = "is_representative_badge", nullable = false)
    private boolean isRepresentativeBadge;      // 대표 뱃지 여부

}
