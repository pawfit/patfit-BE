package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.UpdateRepresentativeBadgeResult;

public record UpdateRepresentativeBadgeResponse(
        Long badgeId,
        String badgeName,
        boolean isRepresentativeBadge
) {
    public static UpdateRepresentativeBadgeResponse from(UpdateRepresentativeBadgeResult result){
        return new UpdateRepresentativeBadgeResponse(
                result.badgeId(),
                result.badgeName(),
                result.isRepresentativeBadge()
        );
    }
}