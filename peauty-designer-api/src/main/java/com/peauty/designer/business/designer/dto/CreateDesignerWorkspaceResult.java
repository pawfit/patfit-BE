package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;

public record CreateDesignerWorkspaceResult(
        String bannerImageUrl,
        String workspaceName,
        Double reviewRating,
        Integer reviewsCount,
        // String scissors,
        String introduceTitle,
        String introduce,
        // TODO: 뱃지는 어떤 도메인에 넣을 것인지 생각
        // List<String> representativeBadgeNames,
        String noticeTitle,
        String notice,
        String address,
        String phoneNumber,
        Integer yearOfExperience,
        List<String> licenses,
        List<PaymentOption> paymentType,
        String openHours,
        String closeHours,
        String openDays,
        String directionGuide
) {

    public static CreateDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        return new CreateDesignerWorkspaceResult(
                designer.getProfileImageUrl(),
                designer.getNickname(),
                workspace.getReviewRating(),
                workspace.getReviewCount(),
                //workspace.getRating().getScissor().toString(),
               workspace.getIntroduceTitle(),
               workspace.getIntroduce(),
               workspace.getNoticeTitle(),
               workspace.getNotice(),
                designer.getAddress(),
                designer.getPhoneNumber(),
                designer.getYearOfExperience(),
                licenses,
                workspace.getPaymentOptions(),
                workspace.getOpenHours(),
                workspace.getCloseHours(),
                workspace.getOpenDays(),
                workspace.getDirectionGuide()
        );
    }
}
