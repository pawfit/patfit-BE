package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.GetAroundWorkspaceResult;
import com.peauty.domain.designer.BadgeColor;
import com.peauty.domain.designer.BadgeType;
import com.peauty.domain.designer.Scissors;

import java.util.List;

public record GetAroundWorkspaceResponse(
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
        List<BadgeResponse> representativeBadges // 변경
//        String scissorsRank
) {
    public static GetAroundWorkspaceResponse from(GetAroundWorkspaceResult result) {
        List<BadgeResponse> badgeResponses = result.representativeBadges().stream()
                .map(BadgeResponse::from)
                .toList();

        return new GetAroundWorkspaceResponse(
                result.workspaceId(),
                result.workspaceName(),
                result.address(),
                result.addressDetail(),
                result.bannerImageUrl(),
                result.reviewCount(),
                result.reviewRating(),
                result.designerId(),
                result.designerName(),
                result.yearOfExperience(),
                badgeResponses
//                result.scissorsRank()
        );
    }

    public record BadgeResponse(
            Long badgeId,
            String badgeName,
            String badgeContent,
            String badgeImageUrl,
            BadgeColor badgeColor,
            BadgeType badgeType
    ) {
        public static BadgeResponse from(GetAroundWorkspaceResult.Badge badge) {
            return new BadgeResponse(
                    badge.badgeId(),
                    badge.badgeName(),
                    badge.badgeContent(),
                    badge.badgeImageUrl(),
                    badge.badgeColor(),
                    badge.badgeType()
            );
        }
    }
}