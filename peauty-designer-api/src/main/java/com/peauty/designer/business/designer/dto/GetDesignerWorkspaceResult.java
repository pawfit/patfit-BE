package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;
import java.util.stream.Collectors;

public record GetDesignerWorkspaceResult(
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
        List<PaymentOption> paymentType,
        String openHours,
        String closeHours,
        String openDays,
        String directionGuide
) {
    public static GetDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        return new GetDesignerWorkspaceResult(
                workspace.getBannerImageUrl(),
                workspace.getWorkspaceName(),
                workspace.getReviewRating(),
                workspace.getReviewCount(),
                workspace.getRating().getScissor(),
                workspace.getIntroduceTitle(),
                workspace.getIntroduce(),
                designer.getBadges().stream()
                        .map(Badge::getBadgeName) // Badge 객체에서 badgeName만 추출
                        .collect(Collectors.toList()),
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
