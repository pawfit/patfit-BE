package com.peauty.customer.business.customer.dto;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;

public record GetAroundWorkspaceResult(
        Long workspaceId,
        String workspaceName,
        String address,
        String addressDetail,
        String bannerImageUrl,
        Integer reviewCount,
        Double reviewRating,
        String designerName,
        Integer yearOfExperience
) {
    public static GetAroundWorkspaceResult from(Workspace workspace, Designer designer) {
        return new GetAroundWorkspaceResult(
                workspace.getWorkspaceId(),
                workspace.getWorkspaceName(),
                workspace.getAddress(),
                workspace.getAddressDetail(),
                workspace.getBannerImageUrl(),
                workspace.getReviewCount(),
                workspace.getReviewRating(),
                designer.getName(),
                designer.getYearOfExperience()
        );
    }
}