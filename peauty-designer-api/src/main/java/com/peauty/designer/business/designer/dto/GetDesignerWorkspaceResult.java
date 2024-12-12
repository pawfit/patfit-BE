package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.*;

import java.util.List;
import java.util.stream.Collectors;

public record GetDesignerWorkspaceResult(
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
    public static GetDesignerWorkspaceResult from(Designer designer, Workspace workspace) {
        List<String> licenses = designer.getLicenses().stream()
                .map(License::getLicenseImageUrl)
                .toList();

        return new GetDesignerWorkspaceResult(
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
                designer.getPhoneNumber(),
                designer.getYearOfExperience(),
                workspace.getOpenHours(),
                workspace.getCloseHours(),
                workspace.getOpenDays(),
                workspace.getDirectionGuide(),
                licenses,
                workspace.getPaymentOptions().stream().map(PaymentOption::getOptionName).toList(),
                designer.getBadges().stream()
                        .map(Badge::getBadgeName)
                        .collect(Collectors.toList())
        );
    }
}
