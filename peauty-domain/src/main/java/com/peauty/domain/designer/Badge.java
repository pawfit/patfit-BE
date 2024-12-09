package com.peauty.domain.designer;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    private Long badgeId;
    private String badgeName;
    private String badgeContent;
    private String badgeImageUrl;
    private Boolean isRepresentativeBadge;
    private BadgeColor badgeColor;
    private BadgeType badgeType;

    public void updateIsRepresentativeBadge(boolean isRepresentativeBadge) {
        this.isRepresentativeBadge = isRepresentativeBadge;
    }
}
