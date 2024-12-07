package com.peauty.designer.presentation.controller.designer.dto;


import com.peauty.designer.business.designer.dto.UpdateRepresentativeBadgeCommand;

public record UpdateRepresentativeBadgeRequest(
// TODO: 프론트 화면이 안 나와서 주석처리
//      String badgeName,
//      String badgeContent,
//      String badgeImageUrl,
      boolean isRepresentativeBadge
//      BadgeColor badgeColor
) {
    public UpdateRepresentativeBadgeCommand toCommand() {
        return new UpdateRepresentativeBadgeCommand(
// TODO: 프론트 화면이 안 나와서 주석처리
//                this.badgeName,
//                this.badgeContent,
//                this.badgeImageUrl,
                this.isRepresentativeBadge
//                this.badgeColor
        );
    }
}
