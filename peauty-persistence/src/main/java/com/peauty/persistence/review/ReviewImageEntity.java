package com.peauty.persistence.review;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    @Lob
    @Column(name = "review_image_url")
    private String reviewImageUrl;

}
