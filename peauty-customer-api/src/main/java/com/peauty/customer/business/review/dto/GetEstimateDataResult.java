package com.peauty.customer.business.review.dto;

import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import lombok.Builder;

// TODO. 사용하지 않을 계획이면 삭제하게
@Builder
public record GetEstimateDataResult(
        Estimate.Profile estimateProfile,
        String designerNickname,
        String workspaceName
) {
    public static GetEstimateDataResult from(Estimate.Profile estimateProfile, Designer designer, Workspace workspace) {
        return GetEstimateDataResult.builder()
                .estimateProfile(estimateProfile)
                .designerNickname(designer.getNickname())
                .workspaceName(workspace.getWorkspaceName())
                .build();
    }
}
