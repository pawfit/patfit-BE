package com.peauty.customer.business.customer.dto;

import com.peauty.domain.designer.*;

import java.util.List;

public record GetAroundWorkspaceResult(
        Long workspaceId,
        String workspaceName,
        String address,
        String addressDetail,
        String bannerImageUrl,
        Integer reviewCount,
        Double reviewRating,
        Long designerId,
        String designerName,
        Integer yearOfExperience,
        List<Badge> representativeBadges // 변경
//        String scissorsRank
) {
    public record Badge(
            Long badgeId,
            String badgeName,
            String badgeContent,
            String badgeImageUrl,
            BadgeColor badgeColor,
            BadgeType badgeType
    ) {
    }

    public static GetAroundWorkspaceResult from(Workspace workspace, Designer designer, List<Badge> badges) {
        return new GetAroundWorkspaceResult(
                workspace.getWorkspaceId(),
                workspace.getWorkspaceName(),
                workspace.getAddress(),
                workspace.getAddressDetail(),
                workspace.getBannerImageUrl(),
                workspace.getReviewCount(),
                workspace.getReviewRating(),
                designer.getDesignerId(),
                designer.getName(),
                designer.getYearOfExperience(),
                badges
//                workspace.getRating().getScissors().getScissorsRank()
        );
    }
}