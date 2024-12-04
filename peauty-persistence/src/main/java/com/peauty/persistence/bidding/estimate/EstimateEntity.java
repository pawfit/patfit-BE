package com.peauty.persistence.bidding.estimate;

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
public class EstimateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidding_thread_id", nullable = false)
    private Long biddingThreadId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "proposal_image_url")
    private String proposalImageUrl;
}
