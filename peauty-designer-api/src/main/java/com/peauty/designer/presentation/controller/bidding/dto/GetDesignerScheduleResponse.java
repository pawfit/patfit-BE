package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetDesignerScheduleResult;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Scissors;
import com.peauty.domain.puppy.Puppy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record GetDesignerScheduleResponse(
        @Schema(description = "디자이너 작업공간 정보")
        GetDesignerScheduleResponseWorkspace workspace,

        @Schema(description = "미용 예약 현황 요약")
        GetDesignerScheduleResponseGroomingSummary groomingSummary,

        @Schema(description = "예약 스레드 목록")
        List<GetDesignerScheduleResponseThread> threads
) {

    @Builder
    public record GetDesignerScheduleResponseWorkspace(
            @Schema(description = "디자이너 ID")
            Long designerId,

            @Schema(description = "디자이너 프로필 이미지 URL")
            String designerProfileImageUrl,

            @Schema(description = "작업공간 이름")
            String workspaceName,

            @Schema(description = "리뷰 개수")
            Integer reviewCount,

            @Schema(description = "리뷰 평점")
            Double reviewRating,

            @Schema(description = "보유 뱃지 목록")
            List<Badge> badges,

            @Schema(description = "작업공간 주소")
            String address,

            @Schema(description = "디자이너 등급")
            Scissors scissors
    ) {}

    @Builder
    public record GetDesignerScheduleResponseGroomingSummary(
            @Schema(description = "완료된 미용 건수")
            Integer endGroomingCount,

            @Schema(description = "오늘 예정된 미용 건수")
            Integer todayGroomingCount,

            @Schema(description = "다음 예정된 미용 건수")
            Integer nextGroomingCount
    ) {}

    @Builder
    public record GetDesignerScheduleResponseThread(
            @Schema(description = "프로세스 ID")
            Long processId,

            @Schema(description = "프로세스 상태")
            String processStatus,

            @Schema(description = "프로세스 생성 시간")
            LocalDateTime processCreatedAt,

            @Schema(description = "프로세스 상태 수정 시간")
            LocalDateTime processStatusModifiedAt,

            @Schema(description = "스레드 ID")
            Long threadId,

            @Schema(description = "스레드 단계")
            String threadStep,

            @Schema(description = "스레드 상태")
            String threadStatus,

            @Schema(description = "스레드 생성 시간")
            LocalDateTime threadCreatedAt,

            @Schema(description = "스레드 단계 수정 시간")
            LocalDateTime threadStepModifiedAt,

            @Schema(description = "미용 스타일")
            String style,

            @Schema(description = "예약금")
            Long depositPrice,

            @Schema(description = "희망 미용 일시")
            String desiredGroomingDateTime,

            @Schema(description = "반려견 프로필 정보")
            Puppy.PuppyProfile puppy
    ) {}

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
                                .style(processProfile.estimateProposal().style())
                                .desiredGroomingDateTime(processProfile.estimateProposal().desiredDateTime())
                                .puppy(processProfile.puppy())
                                .build())
                        .toList()
        );
    }
}