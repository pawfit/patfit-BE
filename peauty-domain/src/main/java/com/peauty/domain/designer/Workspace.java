package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.ReviewRating;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    private Long workspaceId;
    private List<String> bannerImageUrls;
    private String workspaceName;
    private String introduceTitle;
    private String introduce;
    private String noticeTitle;
    private String notice;
    private List<PaymentOption> paymentOptions;
    private String openHours;
    private String closeHours;
    private String openDays;
    private String directionGuide;
    private Integer reviewCount;
    private Double reviewRating;
    private Rating rating;
    private String address;
    private String addressDetail;
    private String phoneNumber;

    private Long designerId;

    public void updateRating(Rating rating) {
        this.rating = rating;
    }

    public void updateBannerImgUrls(List<String> bannerImageUrls) {
        this.bannerImageUrls = bannerImageUrls;
    }
    public void updateWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public void updateWorkspace(Workspace workspace){
        this.workspaceName = workspace.getWorkspaceName();
        this.introduceTitle = workspace.getIntroduceTitle();
        this.introduce = workspace.getIntroduce();
        this.noticeTitle = workspace.getNoticeTitle();
        this.notice = workspace.getNotice();
        this.paymentOptions = workspace.getPaymentOptions();
        this.openHours = workspace.getOpenHours();
        this.closeHours = workspace.getCloseHours();
        this.openDays = workspace.getOpenDays();
        this.directionGuide = workspace.getDirectionGuide();
        this.address = workspace.getAddress();
        this.addressDetail = workspace.getAddressDetail();
        this.phoneNumber = workspace.getPhoneNumber();
    }
    // 리뷰 작성 시 메서드
    // TODO: 동시에 한 워크스페이스에 대한 리뷰가 반영되었을 때 생기는 동시성 문제 처리 해결 필요.

    public void registerReviewStats(ReviewRating newRating) {
        if (reviewCount == null) reviewCount = 0;
        if (reviewRating == null) reviewRating = 0.0;

        reviewRating = ((reviewRating * reviewCount) + newRating.getValue()) / (reviewCount + 1);
        reviewCount += 1;
    }

    // 리뷰 업데이트 시 메서드
    public void updateReviewStats(ReviewRating oldRating, ReviewRating newRating) {
        if (reviewCount == null || reviewCount == 0) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_UPDATE);
        }
        reviewRating = ((reviewRating * reviewCount) - oldRating.getValue() + newRating.getValue()) / reviewCount;
    }

    // 리뷰 삭제 시 메서드
    public void deleteReviewStats(ReviewRating deletedRating) {
        if (reviewCount == null || reviewCount == 0) {
            throw new PeautyException(PeautyResponseCode.INVALID_REVIEW_UPDATE);
        }
        if (reviewCount == 1) {
            reviewRating = 0.0;
            reviewCount = 0;
        } else {
            reviewRating = ((reviewRating * reviewCount) - deletedRating.getValue()) / (reviewCount - 1);
            reviewCount -= 1;
        }
    }

    public String getRepresentativeBannerImageUrl() {
        return getBannerImageUrls().stream()
                .findFirst()
                .orElse(null);
    }
}
