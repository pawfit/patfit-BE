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
    private Long workspaceId;
    private String bannerImageUrl;
    private String workspaceName;
    private String introduceTitle;
    private String introduce;
    private String noticeTitle;
    private String notice;
    private List<PaymentOption> paymentOptions;
    private String openHours;
    private String closeHours;
    private String openDays;
    private String directionGuide;
    private Integer reviewCount;
    private Double reviewRating;
    private Rating rating;

    public void updateRating(Rating rating) {
        this.rating = rating;
    }
}
