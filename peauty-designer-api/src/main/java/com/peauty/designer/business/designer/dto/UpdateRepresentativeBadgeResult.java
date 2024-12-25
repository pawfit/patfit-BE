package com.peauty.designer.business.designer.dto;

public record UpdateRepresentativeBadgeResult(
        Long badgeId,
        String badgeName,
        boolean isRepresentativeBadge
) {
    public static UpdateRepresentativeBadgeResult from(Long badgeId,
                                                       String badgeName,
                                                       boolean isRepresentativeBadge) {

        return new UpdateRepresentativeBadgeResult(
                badgeId,
                badgeName,
                isRepresentativeBadge);
    }
}