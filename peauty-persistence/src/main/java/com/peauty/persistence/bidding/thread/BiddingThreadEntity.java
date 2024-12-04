package com.peauty.persistence.bidding.thread;

import com.peauty.domain.bidding.BiddingThreadStatus;
import com.peauty.domain.bidding.BiddingThreadStep;
import com.peauty.domain.bidding.BiddingThreadTimeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bidding_thread")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BiddingThreadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidding_process_id", nullable = false)
    private Long biddingProcessId;

    @Column(name = "designer_id", nullable = false)
    private Long designerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "step", nullable = false)
    private BiddingThreadStep step;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BiddingThreadStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "step_modified_at", nullable = false)
    private LocalDateTime stepModifiedAt;

    @Column(name = "status_modified_at", nullable = false)
    private LocalDateTime statusModifiedAt;
}