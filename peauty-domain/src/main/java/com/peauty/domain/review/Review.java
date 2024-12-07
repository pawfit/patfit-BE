package com.peauty.domain.review;

import com.peauty.domain.bidding.BiddingThread;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Review {

    private final ID id;                          // 리뷰 ID
    @Getter private final BiddingThread.ID threadId;    // 입찰 스레드 ID
    @Getter private ReviewRating reviewRating;          // 별점
    @Getter private String contentDetail;               // 상세리뷰
    @Getter private ContentGeneral contentGeneral;      // 일반리뷰
    @Getter private List<ReviewImage> reviewImages;     // 리뷰이미지

    public Optional<Review.ID> getId(){
        return Optional.ofNullable(this.id);
    }

    public ID getSavedReviewId() {
        return id;
    }

    public record ID(Long value){
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }

    public void updateRatingService(ReviewRating reviewRating) {
        this.reviewRating = reviewRating;
    }
    public void updateContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }
    public void updateContentGeneral(ContentGeneral contentGeneral) {
        this.contentGeneral = contentGeneral;
    }
    public void updateReviewImageUrl(List<ReviewImage> reviewImages) {
        this.reviewImages = reviewImages;
    }

}
