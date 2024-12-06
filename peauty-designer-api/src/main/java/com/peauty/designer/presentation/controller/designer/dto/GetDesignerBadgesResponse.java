package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.GetDesignerBadgesResult;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.BadgeColor;

import java.util.List;

public record GetDesignerBadgesResponse(
        List<BadgeResponse> acquiredBadges,          // 획득한 뱃지
        List<BadgeResponse> unacquiredBadges,        // 획득하지 않은 뱃지
        List<BadgeResponse> representativeBadges     // 대표 뱃지
) {
    public static GetDesignerBadgesResponse from(GetDesignerBadgesResult result) {
        return new GetDesignerBadgesResponse(
                result.acquiredBadges().stream().map(BadgeResponse::from).toList(),
                result.unacquiredBadges().stream().map(BadgeResponse::from).toList(),
                result.representativeBadges().stream().map(BadgeResponse::from).toList()
        );
    }

    public record BadgeResponse(
            Long badgeId,
            String badgeName,
            String badgeContent,
            String badgeImageUrl,
            BadgeColor badgeColor
    ) {
        public static BadgeResponse from(Badge badge) {
            return new BadgeResponse(
                    badge.getBadgeId(),
                    badge.getBadgeName(),
                    badge.getBadgeContent(),
                    badge.getBadgeImageUrl(),
                    badge.getBadgeColor()
            );
        }
    }
}