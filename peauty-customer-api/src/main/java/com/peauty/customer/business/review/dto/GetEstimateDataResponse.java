package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.Estimate;

public record GetEstimateDataResponse(
        EstimateProfile estimateProfile,
        String designerNickname,
        String workspaceName,
        String workspaceBannerUrl
) {
    public static GetEstimateDataResponse from(GetEstimateDataResult result) {
        return new GetEstimateDataResponse(
                EstimateProfile.from(result.estimateProfile()),
                result.designerNickname(),
                result.workspaceName(),
                result.workspaceBannerUrl()
        );
    }

    public record EstimateProfile(
            Long id,
            String content,
            String availableGroomingDate,
            String estimatedDuration,
            Long estimatedCost
//            List<String> imageUrls
    ) {
        public static EstimateProfile from(Estimate.Profile profile) {
            return new EstimateProfile(
                    profile.estimateId(),
                    profile.content(),
                    profile.availableGroomingDate(),
                    profile.estimatedDuration(),
                    profile.estimatedCost()
//                    profile.imageUrls()
            );
        }
    }
}