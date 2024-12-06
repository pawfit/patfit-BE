package com.peauty.customer.business.customer.dto;

import com.peauty.domain.designer.*;

import java.util.List;
import java.util.Optional;

public record GetAroundWorkspaceResult(
        Long workspaceId,
        String workspaceName,
        String address,
        String addressDetail,
        String bannerImageUrl,
        Integer reviewCount,
        Double reviewRating,
        String designerName,
        Integer yearOfExperience,
        List<String> representativeBadgesName,
        Scissors scissorsRank
) {
    public static GetAroundWorkspaceResult from(Workspace workspace, Designer designer, List<String> badges) {

        return new GetAroundWorkspaceResult(
                workspace.getWorkspaceId(),
                workspace.getWorkspaceName(),
                workspace.getAddress(),
                workspace.getAddressDetail(),
                workspace.getBannerImageUrl(),
                workspace.getReviewCount(),
                workspace.getReviewRating(),
                designer.getName(),
                designer.getYearOfExperience(),
                badges,
                workspace.getRating().getScissors()
        );
    }
}