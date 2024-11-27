package com.peauty.persistence.designer;

import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designer_info")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerInfoEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private DesignerEntity designer;

    @Column(name = "introduce", length = 255)
    private String introduce;

    @Column(name = "notice", length = 255)
    private String notice;

    @Lob
    @Column(name = "banner_image_url")
    private String bannerImageUrl;

    @Column(name = "career")
    private Integer career;

}
