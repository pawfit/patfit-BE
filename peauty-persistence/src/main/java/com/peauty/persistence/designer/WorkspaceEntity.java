package com.peauty.persistence.designer;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "workspace")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WorkspaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private DesignerEntity designer;

    @Column(name = "introduce_title", length = 125)
    private String introduceTitle;

    @Column(name = "introduce", length = 255)
    private String introduce;

    @Column(name = "notice", length = 255)
    private String notice;

    @Column(name = "notice_title", length = 125)
    private String noticeTitle;

    @Column(name = "workspace_name", length = 125, nullable = false)
    private String workspaceName;

    @Lob
    @Column(name = "banner_image_url")
    private String bannerImageUrl;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "open_hours", length = 10, nullable = false)
    private String openHours;

    @Column(name = "close_hours", length = 10, nullable = false)
    private String closeHours;

    @Column(name = "open_days", length = 10, nullable = false)
    private String openDays;

    @Column(name = "payment_options", length = 255, nullable = false)
    private String paymentOptions;

    @Column(name = "direction_guide", length = 255, nullable = false)
    private String directionGuide;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<LicenseEntity> licenses;

}
