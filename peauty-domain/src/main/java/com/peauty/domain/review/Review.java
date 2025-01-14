package com.peauty.domain.review;

import com.peauty.domain.bidding.BiddingThread;
import lombok.*;

import java.time.LocalDate;
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
    @Getter private List<ContentGeneral> contentGenerals;      // 일반리뷰
    @Getter private List<ReviewImage> reviewImages;     // 리뷰이미지
    @Getter private LocalDate reviewCreatedAt;      // 리뷰 작성 날짜

    // TODO: reviewerNickname은 Customer 도메인에서, groomingStyle은 EstimateProposal에서 가져올 수 있도록 하기
    @Getter private final String reviewerNickname;

    @Getter private final String groomingStyle;

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
    public void updateContentGeneral(List<ContentGeneral> contentGenerals) {
        this.contentGenerals = contentGenerals;
    }
    public void updateReviewImageUrl(List<ReviewImage> reviewImages) {
        this.reviewImages = reviewImages;
    }

    public void updateReview(ReviewRating reviewRating, String contentDetail, List<ContentGeneral> contentGenerals, List<ReviewImage> reviewImages) {
        this.reviewRating = reviewRating;
        this.contentDetail = contentDetail;
        this.contentGenerals = contentGenerals;
        this.reviewImages = reviewImages;
    }

    /* TODO: 리팩토링 예정 : 리뷰 전체 조회 건
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
    }*/


}
