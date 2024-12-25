package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Badge;

import java.util.List;

public record GetDesignerBadgesResult(
        List<Badge> acquiredBadges,          // 획득한 뱃지
        List<Badge> unacquiredBadges,        // 획득하지 않은 뱃지
        List<Badge> representativeBadges     // 대표 뱃지
) {

    public static GetDesignerBadgesResult from(List<Badge> acquiredBadges,
                                               List<Badge> unacquiredBadges,
                                               List<Badge> representativeBadges) {

        return new GetDesignerBadgesResult(
                acquiredBadges,
                unacquiredBadges,
                representativeBadges);
    }
}