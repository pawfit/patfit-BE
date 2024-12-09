package com.peauty.domain.review;

import com.peauty.domain.bidding.EstimateProposal;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewAdditionalInfo(
        String workspaceName,
        String workspaceBannerImageUrl,
        String designerNickname,
        String groomingType,
        Long totalCost,
        LocalDateTime serviceDate,
        String totalGroomingBodyType,
        String totalGroomingFaceType
) {
    public static ReviewAdditionalInfo from(
            String workspaceName,
            String workspaceBannerImageUrl,
            String designerNickname,
            EstimateProposal.ReviewProfile reviewProfile
    ) {
        return new ReviewAdditionalInfo(
                workspaceName,
                workspaceBannerImageUrl,
                designerNickname,
                reviewProfile.type(),
                reviewProfile.desiredCost(),
                reviewProfile.desiredDateTime(),
                reviewProfile.totalGroomingBodyType(),
                reviewProfile.totalGroomingFaceType()
        );
    }
}