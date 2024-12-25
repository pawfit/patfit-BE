package com.peauty.persistence.designer.workspace;

import com.peauty.domain.designer.PaymentOption;
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

    @Column(name = "designer_id", nullable = false)
    private Long designerId;

    @Column(name = "introduce_title", length = 125)
    private String introduceTitle;

    @Column(name = "introduce", length = 255)
    private String introduce;

    @Column(name = "notice", length = 255)
    private String notice;

    @Column(name = "notice_title", length = 125)
    private String noticeTitle;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "address_detail", length = 255, nullable = false)
    private String addressDetail;

    @Column(name = "workspace_name", length = 125, nullable = false)
    private String workspaceName;

    @Column(name = "open_hours",  nullable = false)
    private String openHours;

    @Column(name = "close_hours", nullable = false)
    private String closeHours;

    @Column(name = "open_days",  nullable = false)
    private String openDays;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_options", nullable = false)
    private List<PaymentOption> paymentOptions;

    @Column(name = "direction_guide", nullable = false)
    private String directionGuide;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @Column(name = "review_rating", nullable = false)
    private Double reviewRating;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}
