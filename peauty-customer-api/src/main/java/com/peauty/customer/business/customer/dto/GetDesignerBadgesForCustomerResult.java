package com.peauty.customer.business.customer.dto;

import com.peauty.domain.designer.Badge;

import java.util.List;

public record GetDesignerBadgesForCustomerResult(
        List<Badge> acquiredBadges,
        List<Badge> representativeBadges
) {
    public static GetDesignerBadgesForCustomerResult from(List<Badge> acquiredBadges,
                                                          List<Badge> representativeBadges) {
        return new GetDesignerBadgesForCustomerResult(
                acquiredBadges,
                representativeBadges
        );
    }
}