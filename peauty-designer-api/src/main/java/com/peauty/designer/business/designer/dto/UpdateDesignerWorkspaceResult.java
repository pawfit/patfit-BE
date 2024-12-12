package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;
import java.util.stream.Collectors;

public record UpdateDesignerWorkspaceResult(
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
        List<String> paymentOptions,
        List<String> representativeBadgeNames
) {
    public static UpdateDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        return new UpdateDesignerWorkspaceResult(
                designer.getDesignerId(),
                workspace.getWorkspaceId(),
                workspace.getBannerImageUrl(),
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
                workspace.getPhoneNumber(),
                designer.getYearOfExperience(),
                workspace.getOpenHours(),
                workspace.getCloseHours(),
                workspace.getOpenDays(),
                workspace.getDirectionGuide(),
                licenses,
                workspace.getPaymentOptions().stream().map(PaymentOption::getOptionName).toList(),
                designer.getBadges().stream()
                        .map(Badge::getBadgeName) // Badge 객체에서 badgeName만 추출
                        .collect(Collectors.toList())
        );
    }
}
