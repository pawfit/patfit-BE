package com.peauty.domain.review;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private Long reviewId;                      // 리뷰 ID
    private Long customerId;                    // 고객 ID
    private Long designerId;                    // 미용사 ID
    private Rating rating;        // 별점
    private String contentDetail;               // 상세리뷰
    private ContentGeneral contentGeneral;      // 일반리뷰
    private List<String> reviewImageUrl;        // 리뷰이미지

    public void updateRatingService(Rating rating) {
        this.rating = rating;
    }
    public void updateContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }
    public void updateContentGeneral(ContentGeneral contentGeneral) {
        this.contentGeneral = contentGeneral;
    }
    public void updateReviewImageUrl(List<String> reviewImageUrl) {
        this.reviewImageUrl = reviewImageUrl;
    }

}
