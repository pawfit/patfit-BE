package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Badge;

public record UpdateRepresentativeBadgeCommand(
// TODO: 프론트 화면이 안 나와서 주석처리
//        String badgeName,
//        String badgeContent,
//        String badgeImageUrl,
        boolean isRepresentativeBadge
//        BadgeColor badgeColor
) {
    public static Badge toBadge(UpdateRepresentativeBadgeCommand command){
        return Badge.builder()
// TODO: 프론트 화면이 안 나와서 주석처리
//                .badgeName(command.badgeName())
//                .badgeContent(command.badgeContent())
//                .badgeImageUrl(command.badgeImageUrl())
                .isRepresentativeBadge(command.isRepresentativeBadge())
//                .badgeColor(command.badgeColor())
                .build();
    }
}
