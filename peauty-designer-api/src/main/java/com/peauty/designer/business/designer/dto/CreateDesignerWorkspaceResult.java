package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;

public record CreateDesignerWorkspaceResult(
        Long designerId,
        Long workspaceId,
        String bannerImageUrl,
        String workspaceName,
        Double reviewRating,
        Integer reviewsCount,
        Scissors scissors,
        String introduceTitle,
        String introduce,
        String noticeTitle,
        String notice,
        String address,
        String addressDetail,
        String phoneNumber,
        Integer yearOfExperience,
        String openHours,
        String closeHours,
        String openDays,
        String directionGuide,
        List<String> licenses,
        List<PaymentOption> paymentOptions,
        List<String> representativeBadgeNames
) {

    public static CreateDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        List<String> badges = designer.getBadges().stream()
                .map(Badge::getBadgeContent)
                .toList();

        // 필드 순서에 맞춘 매핑
        return new CreateDesignerWorkspaceResult(
                designer.getDesignerId(),
                workspace.getWorkspaceId(),
                designer.getProfileImageUrl(),
                workspace.getWorkspaceName(),
                workspace.getReviewRating(),
                workspace.getReviewCount(),
                workspace.getRating().getScissors(),
                workspace.getIntroduceTitle(),
                workspace.getIntroduce(),
                workspace.getNoticeTitle(),
                workspace.getNotice(),
                workspace.getAddress(),
                workspace.getAddressDetail(),
                designer.getPhoneNumber(),
                designer.getYearOfExperience(),
                workspace.getOpenHours(),
                workspace.getCloseHours(),
                workspace.getOpenDays(),
                workspace.getDirectionGuide(),
                licenses,
                workspace.getPaymentOptions(),
                badges
        );
    }
}
