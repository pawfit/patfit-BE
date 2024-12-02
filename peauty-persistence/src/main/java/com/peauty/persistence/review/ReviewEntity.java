package com.peauty.persistence.review;

import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewRating;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidding_thread_id", nullable = false)
    private Long biddingThreadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", nullable = false)
    private ReviewRating reviewRating;

    @Column(name = "content_detail", nullable = false)
    private String contentDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_general")
    private ContentGeneral contentGeneral;

}
