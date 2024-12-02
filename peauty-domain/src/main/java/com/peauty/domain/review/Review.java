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
    private Long biddingThreadId;               // 입찰 스레드 ID
    private ReviewRating reviewRating;          // 별점
    private String contentDetail;               // 상세리뷰
    private ContentGeneral contentGeneral;      // 일반리뷰
    private List<String> reviewImages;          // 리뷰이미지

    public void updateRatingService(ReviewRating reviewRating) {
        this.reviewRating = reviewRating;
    }
    public void updateContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }
    public void updateContentGeneral(ContentGeneral contentGeneral) {
        this.contentGeneral = contentGeneral;
    }
    public void updateReviewImageUrl(List<String> reviewImages) {
        this.reviewImages = reviewImages;
    }

}
