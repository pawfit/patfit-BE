package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.GetDesignerBadgesForCustomerResult;
import com.peauty.domain.designer.Badge;

import java.util.List;

public record GetDesignerBadgesForCustomerResponse(
        List<BadgeResponse> acquiredBadges,
        List<BadgeResponse> representativeBadges
) {
    public static GetDesignerBadgesForCustomerResponse from(GetDesignerBadgesForCustomerResult result) {
        return new GetDesignerBadgesForCustomerResponse(
                result.acquiredBadges().stream()
                        .map(BadgeResponse::from)
                        .toList(),
                result.representativeBadges().stream()
                        .map(BadgeResponse::from)
                        .toList()
        );
    }

    public record BadgeResponse(
            Long badgeId,
            String badgeName,
            String badgeContent,
            String badgeImageUrl
    ) {
        public static BadgeResponse from(Badge badge) {
            return new BadgeResponse(
                    badge.getBadgeId(),
                    badge.getBadgeName(),
                    badge.getBadgeContent(),
                    badge.getBadgeImageUrl()
            );
        }
    }
}