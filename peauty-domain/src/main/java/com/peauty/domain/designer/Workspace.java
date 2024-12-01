package com.peauty.domain.designer;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    Long workspaceId;
    String bannerImageUrl;
    String workspaceName;
    String introduceTitle;
    String introduce;
    List<String> representativeBadgeNames;
    String noticeTitle;
    String notice;
    Integer yearOfExperience;
    List<License> licenses;
    List<String> paymentOptions;
    String openHours;
    String closeHours;
    String openDays;
    String directionGuide;

    public static Workspace getFirstWorkspace() {
        return Workspace.builder()
                .workspaceId(0L)
                .build();
    }
}
