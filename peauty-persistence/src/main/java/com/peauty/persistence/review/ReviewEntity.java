package com.peauty.persistence.review;

import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Rating;
import com.peauty.persistence.config.BaseTimeEntity;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.designer.DesignerEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private DesignerEntity designer;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private Rating rating;

    @Column(name = "content_detail", nullable = false)
    private String contentDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_general")
    private ContentGeneral contentGeneral;

}
