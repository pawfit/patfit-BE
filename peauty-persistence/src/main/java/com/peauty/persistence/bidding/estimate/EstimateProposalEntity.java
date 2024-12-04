package com.peauty.persistence.bidding.estimate;

import com.peauty.domain.bidding.GroomingType;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long biddingProcessId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroomingType type;

    @Column(length = 1000)
    private String detail;

    @OneToMany(mappedBy = "estimateProposal")
    @Builder.Default
    private List<EstimateProposalImageEntity> images = new ArrayList<>();

    @Column(name = "desired_cost")
    private Long desiredCost;

    @Column(name = "desired_date_time")
    private LocalDateTime desiredDateTime;

    @Column
    private String body;

    @Column
    private String face;
}
