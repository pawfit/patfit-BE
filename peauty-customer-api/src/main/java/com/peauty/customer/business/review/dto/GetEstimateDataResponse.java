package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.Estimate;
import lombok.Builder;

// TODO. 여기도 사용하지 않을거면 삭제하기
@Builder
public record GetEstimateDataResponse(
        EstimateProfile estimateProfile,
        String designerNickname,
        String workspaceName,
        String workspaceBannerUrl
) {
    public static GetEstimateDataResponse from(GetEstimateDataResult result) {
        return GetEstimateDataResponse.builder()
                .estimateProfile(EstimateProfile.from(result.estimateProfile()))
                .designerNickname(result.designerNickname())
                .workspaceName(result.workspaceName())
                //.workspaceBannerUrl(result.workspaceBannerUrl())
                .build();
    }

    @Builder
    public record EstimateProfile(
            Long id,
            String content,
            String availableGroomingDate,
            String estimatedDuration,
            Long estimatedCost
            // List<String> imageUrls
    ) {
        public static EstimateProfile from(Estimate.Profile profile) {
            return EstimateProfile.builder()
                    .id(profile.estimateId())
                    .content(profile.content())
                    .availableGroomingDate(profile.availableGroomingDate())
                    .estimatedDuration(profile.estimatedDuration())
                    .estimatedCost(profile.estimatedCost())
                    // .imageUrls(profile.imageUrls())
                    .build();
        }
    }
}
