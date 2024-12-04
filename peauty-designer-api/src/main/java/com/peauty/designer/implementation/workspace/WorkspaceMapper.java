package com.peauty.designer.implementation.workspace;

import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Scissor;
import com.peauty.domain.designer.Workspace;
import com.peauty.persistence.designer.RatingEntity;
import com.peauty.persistence.designer.WorkspaceEntity;

import java.util.Optional;

public class WorkspaceMapper {
    public static WorkspaceEntity toEntity(Workspace workspace, Long designerId) {
        return WorkspaceEntity.builder()
                .id(workspace.getWorkspaceId())
                .designerId(designerId)
                .noticeTitle(workspace.getNoticeTitle())
                .notice(workspace.getNotice())
                .introduceTitle(workspace.getIntroduceTitle())
                .introduce(workspace.getIntroduce())
                .address(workspace.getAddress())
                .addressDetail(workspace.getAddressDetail())
                .workspaceName(workspace.getWorkspaceName())
                .bannerImageUrl(workspace.getBannerImageUrl())
                .openHours(workspace.getOpenHours())
                .closeHours(workspace.getCloseHours())
                .openDays(workspace.getOpenDays())
                .paymentOptions(workspace.getPaymentOptions())
                .directionGuide(workspace.getDirectionGuide())
                .reviewCount(Optional.ofNullable(workspace.getReviewCount())
                                .orElse(0))
                .reviewRating(Optional.ofNullable(workspace.getReviewRating())
                                .orElse(0.0))
                .build();
    }

    public static Workspace toDomain(WorkspaceEntity workspaceEntity) {
        return Workspace.builder()
                .workspaceId(workspaceEntity.getId())
                .noticeTitle(workspaceEntity.getNoticeTitle())
                .notice(workspaceEntity.getNotice())
                .introduceTitle(workspaceEntity.getIntroduceTitle())
                .introduce(workspaceEntity.getIntroduce())
                .address(workspaceEntity.getAddress())
                .addressDetail(workspaceEntity.getAddressDetail())
                .workspaceName(workspaceEntity.getWorkspaceName())
                .bannerImageUrl(workspaceEntity.getBannerImageUrl())
                .openHours(workspaceEntity.getOpenHours())
                .closeHours(workspaceEntity.getCloseHours())
                .openDays(workspaceEntity.getOpenDays())
                .paymentOptions(workspaceEntity.getPaymentOptions())
                .directionGuide(workspaceEntity.getDirectionGuide())
                .reviewCount(workspaceEntity.getReviewCount())
                .reviewRating(workspaceEntity.getReviewRating())
                .build();
    }

    public static Rating toRatingDomain(RatingEntity rating) {
        if (rating == null) {
            return Rating.builder()
                    .ratingId(0L)
                    .totalScore(0.0)
                    .scissor(Scissor.NONE)
                    .build();
        }

        return Rating.builder()
                .ratingId(rating.getId())
                .totalScore(rating.getTotalScore())
                .scissor(rating.getScissor())
                .build();
    }
}
