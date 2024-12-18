package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetDesignerScheduleResult;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Scissors;
import com.peauty.domain.puppy.Puppy;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record GetDesignerScheduleResponse(
        GetDesignerScheduleResponseWorkspace workspace,
        GetDesignerScheduleResponseGroomingSummary groomingSummary,
        List<GetDesignerScheduleResponseThread> threads
) {

    @Builder
    public record GetDesignerScheduleResponseWorkspace(
            Long designerId,
            String designerProfileImageUrl,
            String workspaceName,
            Integer reviewCount,
            Double reviewRating,
            List<Badge> badges,
            String address,
            Scissors scissors
    ) {
    }

    @Builder
    public record GetDesignerScheduleResponseGroomingSummary(
            Integer endGroomingCount,
            Integer todayGroomingCount,
            Integer nextGroomingCount
    ) {
    }

    @Builder
    public record GetDesignerScheduleResponseThread(
            Long processId,
            String processStatus,
            LocalDateTime processCreatedAt,
            LocalDateTime processStatusModifiedAt,
            Long threadId,
            String threadStep,
            String threadStatus,
            LocalDateTime threadCreatedAt,
            LocalDateTime threadStepModifiedAt,
            Long depositPrice,
            String desiredGroomingDateTime,
            Puppy.PuppyProfile puppy
    ) {
    }

    public static GetDesignerScheduleResponse from(GetDesignerScheduleResult result) {
        return new GetDesignerScheduleResponse(
                GetDesignerScheduleResponseWorkspace.builder()
                        .designerId(result.designerProfile().designerId())
                        .designerProfileImageUrl(result.designerProfile().profileImageUrl())
                        .workspaceName(result.designerProfile().workspaceName())
                        .reviewCount(result.designerProfile().reviewCount())
                        .reviewRating(result.designerProfile().reviewRating())
                        .badges(result.designerProfile().badges())
                        .address(result.designerProfile().address())
                        .scissors(result.designerProfile().scissors())
                        .build(),
                GetDesignerScheduleResponseGroomingSummary.builder()
                        .endGroomingCount(result.groomingSummary().endGroomingCount())
                        .todayGroomingCount(result.groomingSummary().todayGroomingCount())
                        .nextGroomingCount(result.groomingSummary().nextGroomingCount())
                        .build(),
                result.processProfiles().stream()
                        .map(processProfile -> GetDesignerScheduleResponseThread.builder()
                                .processId(processProfile.processId())
                                .processStatus(processProfile.processStatus())
                                .processCreatedAt(processProfile.processCreatedAt())
                                .processStatusModifiedAt(processProfile.processStatusModifiedAt())
                                .threadId(processProfile.threadInfo().threadId())
                                .threadStep(processProfile.threadInfo().threadStep())
                                .threadStatus(processProfile.threadInfo().threadStatus())
                                .threadCreatedAt(processProfile.threadInfo().threadCreatedAt())
                                .threadStepModifiedAt(processProfile.threadInfo().threadStepModifiedAt())
                                .depositPrice(processProfile.estimate().depositPrice())
                                .desiredGroomingDateTime(processProfile.estimateProposal().desiredDateTime())
                                .puppy(processProfile.puppy())
                                .build())
                        .toList()
        );
    }
}
