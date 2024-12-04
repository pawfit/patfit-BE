package com.peauty.persistence.bidding.process;

import com.peauty.domain.bidding.BiddingProcessStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bidding_process")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BiddingProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puppy_id", nullable = false)
    private Long puppyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BiddingProcessStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status_modified_at", nullable = false)
    private LocalDateTime statusModifiedAt;
}