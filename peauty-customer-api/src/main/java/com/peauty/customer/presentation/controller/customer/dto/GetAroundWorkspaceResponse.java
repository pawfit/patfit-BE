package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.customer.dto.GetAroundWorkspaceResult;
import com.peauty.domain.designer.Scissors;

import java.util.List;

public record GetAroundWorkspaceResponse(
        Long workspaceId,
        String workspaceName,
        String address,
        String addressDetail,
        String bannerImageUrl,
        Integer reviewCount,
        Double reviewRating,
        String designerName,
        Integer yearOfExperience,
//        List<String> representativeBadgesName,
        Scissors scissorsRank
) {
    public static GetAroundWorkspaceResponse from(GetAroundWorkspaceResult result) {
        return new GetAroundWorkspaceResponse(
                result.workspaceId(),
                result.workspaceName(),
                result.address(),
                result.addressDetail(),
                result.bannerImageUrl(),
                result.reviewCount(),
                result.reviewRating(),
                result.designerName(),
                result.yearOfExperience(),
//                result.representativeBadgesName(),
                result.scissorsRank()
        );
    }
}