package com.peauty.domain.designer;

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
    private String bannerImageUrl;
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

    public void updateRating(Rating rating) {
        this.rating = rating;
    }

    public void updateBannerImgUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }
    public void updateWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public void updateWorkspace(Workspace workspace){
        this.bannerImageUrl = workspace.getBannerImageUrl();
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
}
