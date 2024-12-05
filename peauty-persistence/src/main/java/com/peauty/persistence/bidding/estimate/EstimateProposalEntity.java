package com.peauty.persistence.bidding.estimate;

import com.peauty.domain.bidding.GroomingType;
import com.peauty.domain.bidding.TotalGroomingBodyType;
import com.peauty.domain.bidding.TotalGroomingFaceType;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estimate_proposal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EstimateProposalEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidding_process_id")
    private Long processId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroomingType type;

    @Column(length = 1000)
    private String detail;

    @Column(name = "desired_cost")
    private Long desiredCost;

    @Column(name = "desired_date_time")
    private LocalDateTime desiredDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "total_grooming_body_type")
    private TotalGroomingBodyType totalGroomingBodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "total_grooming_face_type")
    private TotalGroomingFaceType totalGroomingFaceType;
}
