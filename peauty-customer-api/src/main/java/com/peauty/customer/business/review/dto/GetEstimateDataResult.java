package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;

public record GetEstimateDataResult(
        Estimate.Profile estimateProfile,
        String designerNickname,
        String workspaceName,
        String workspaceBannerUrl
) {
    public static GetEstimateDataResult from(Estimate.Profile estimateProfile, Designer designer, Workspace workspace) {
        return new GetEstimateDataResult(
                estimateProfile,
                designer.getNickname(),
                workspace.getWorkspaceName(),
                workspace.getBannerImageUrl()
        );
    }
}