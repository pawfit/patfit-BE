package com.peauty.domain.review;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.customer.Customer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Getter private LocalDate reviewCreatedAt;      // 리뷰 작성 날짜

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

    public void updateReview(ReviewRating reviewRating, String contentDetail, ContentGeneral contentGeneral, List<ReviewImage> reviewImages) {
        this.reviewRating = reviewRating;
        this.contentDetail = contentDetail;
        this.contentGeneral = contentGeneral;
        this.reviewImages = reviewImages;
    }

    @Builder
    public record Profile(
            Long reviewId,
            Long threadId,
            Double reiewRating,
            String contentDetail,
            String contentGeneral,
            List<String> reviewImages,
            LocalDate reviewCreatedAt,
            Customer.Profile customer,
            Estimate.Profile estimate,
            String style
    ){
    }

    public Profile getProfile(Customer.Profile customerProfile, Estimate.Profile estimateProfile, String style){
        return Profile.builder()
                .reviewId(id.value())
                .threadId(threadId.value())
                .reiewRating(reviewRating.getValue())
                .contentDetail(contentDetail)
                .contentGeneral(contentGeneral.getContentGeneralReview())
                .reviewImages(reviewImages.stream().map(ReviewImage::getImageUrl).toList())
                .reviewCreatedAt(reviewCreatedAt)
                .customer(customerProfile)
                .estimate(estimateProfile)
                .style(style)
                .build();
    }


}
