package com.peauty.customer.business.workspace.dto;

import com.peauty.domain.designer.*;
import lombok.Builder;

import java.util.List;


@Builder
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
                workspace.getRepresentativeBannerImageUrl(),
                workspace.getReviewCount(),
                workspace.getReviewRating(),
                designer.getDesignerId(),
                designer.getName(),
                designer.getYearsOfExperience(),
                badges
//                workspace.getRating().getScissors().getScissorsRank()
        );
    }
}