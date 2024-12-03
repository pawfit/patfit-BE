package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;

public record CreateDesignerWorkspaceResult(
        String bannerImageUrl,
        String workspaceName,
        Double reviewRating,
        Integer reviewsCount,
        Scissor scissor,
        String introduceTitle,
        String introduce,
        List<String> representativeBadgeNames,
        String noticeTitle,
        String notice,
        String address,
        String phoneNumber,
        Integer yearOfExperience,
        List<String> licenses,
        List<PaymentOption> paymentOptions,
        String openHours,
        String closeHours,
        String openDays,
        String directionGuide) {

    public static CreateDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        List<String> badges = designer.getBadges().stream()
                .map(Badge::getBadgeContent)
                .toList();

        // 필드 순서에 맞춘 매핑
        return new CreateDesignerWorkspaceResult(
                designer.getProfileImageUrl(),
                workspace.getWorkspaceName(),
                workspace.getReviewRating(),
                workspace.getReviewCount(),
                workspace.getRating().getScissor(),
                workspace.getIntroduceTitle(),
                workspace.getIntroduce(),
                badges,
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
