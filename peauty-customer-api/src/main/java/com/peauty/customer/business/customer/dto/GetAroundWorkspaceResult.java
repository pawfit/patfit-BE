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

/*  TODO
        // 대표뱃지가 없을 경우 빈 리스트 반환
        List<String> representativeBadgesName = Optional.ofNullable(designer.getBadges())
                .orElse(List.of()) // Null이면 빈 리스트 반환
                .stream()
                .filter(Badge::getIsRepresentativeBadge)
                .map(Badge::getBadgeName)
                .toList();
*/

/* TODO
        // Designer의 대표 뱃지를 필터링
        List<String> representativeBadgesName = designer.getBadges().stream()
                .filter(Badge::getIsRepresentativeBadge)
                .map(Badge::getBadgeName)
                .toList();
*/

/*  TODO
        rating 또는 scissors가 없을 경우 NONE 반환
        Scissors scissorsRank = Optional.ofNullable(workspace.getRating())
                .map(Rating::getScissors)
                .orElse(Scissors.NONE);*/

/*  TODO
        뱃지가 없을 경우 빈 리스트 반환
        List<String> representativeBadgesName = designer.getBadges() != null
                ? designer.getBadges().stream()
                .filter(Badge::getIsRepresentativeBadge)
                .map(Badge::getBadgeContent)
                .toList()
                : Collections.emptyList();*/

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