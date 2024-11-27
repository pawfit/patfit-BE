package com.peauty.persistence.designer;

import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badge")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BadgeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "badge_name", length = 15, nullable = false)
    private String badgeName;

    @Column(name = "badge_content", length = 255, nullable = false)
    private String badgeContent;

    @Lob
    @Column(name = "badge_image_url", nullable = false)
    private String badgeImageUrl;

}