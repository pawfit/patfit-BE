package com.peauty.persistence.bidding.estimate;

import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "estimate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EstimateEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidding_thread_id", nullable = false)
    private Long biddingThreadId;

    @Column(name = "content")
    private String content;

    @Column(name = "available_grooming_date")
    private String availableGroomingDate;

    @Column(name = "estimate_duration")
    private String estimatedDuration;

    @Column(name = "cost", nullable = false)
    private Long estimatedCost;
}
