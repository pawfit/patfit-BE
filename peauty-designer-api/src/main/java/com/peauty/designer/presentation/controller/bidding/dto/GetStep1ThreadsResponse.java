package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetThreadsByStepResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GetStep1ThreadsResponse(
        @Schema(description = "스레드 목록")
        List<GetStep1ThreadsResponseThread> threads
) {

    @Builder
    public record GetStep1ThreadsResponseThread(
            @Schema(description = "프로세스 ID", example = "1")
            Long processId,

            @Schema(description = "프로세스 상태", example = "정상")
            String processStatus,

            @Schema(description = "프로세스 생성 시간", example = "2024-12-19T10:30:00")
            LocalDateTime processCreatedAt,

            @Schema(description = "프로세스 상태 수정 시간", example = "2024-12-19T11:00:00")
            LocalDateTime processStatusModifiedAt,

            @Schema(description = "스레드 ID", example = "5")
            Long threadId,

            @Schema(description = "스레드 단계", example = "견적요청")
            String threadStep,

            @Schema(description = "스레드 상태", example = "정상")
            String threadStatus,

            @Schema(description = "스레드 생성 시간", example = "2024-12-19T10:30:00")
            LocalDateTime threadCreatedAt,

            @Schema(description = "스레드 단계 수정 시간", example = "2024-12-19T11:00:00")
            LocalDateTime threadStepModifiedAt,

            @Schema(description = "반려견 정보")
            GetStep1ThreadsResponsePuppy puppy
    ) {
    }

    @Builder
    public record GetStep1ThreadsResponsePuppy(
            @Schema(description = "반려견 ID", example = "10")
            Long puppyId,

            @Schema(description = "고객 ID", example = "100")
            Long customerId,

            @Schema(description = "반려견 이름", example = "멍멍이")
            String name,

            @Schema(description = "견종", example = "말티즈")
            String breed,

            @Schema(description = "체중(g)", example = "3500")
            Long weight,

            @Schema(description = "성별", example = "M")
            String sex,

            @Schema(description = "나이", example = "3")
            Integer age,

            @Schema(description = "생년월일", example = "2021-01-01")
            LocalDate birthdate,

            @Schema(description = "프로필 이미지 URL", example = "https://example.com/images/puppy.jpg")
            String profileImageUrl,

            @Schema(description = "반려견 크기", example = "소형")
            String puppySize,

            @Schema(
                    description = "질병 목록",
                    example = """
                            ["알레르기", "관절염"]
                            """
            )
            List<String> diseases
    ) {
    }

    public static GetStep1ThreadsResponse from(GetThreadsByStepResult result) {
        return new GetStep1ThreadsResponse(
                result.threads().stream()
                        .map(thread -> GetStep1ThreadsResponseThread.builder()
                                .processId(thread.processId())
                                .processStatus(thread.processStatus())
                                .processCreatedAt(thread.processCreatedAt())
                                .processStatusModifiedAt(thread.processStatusModifiedAt())
                                .threadId(thread.threadInfo().threadId())
                                .threadStep(thread.threadInfo().threadStep())
                                .threadStatus(thread.threadInfo().threadStatus())
                                .threadCreatedAt(thread.threadInfo().threadCreatedAt())
                                .threadStepModifiedAt(thread.threadInfo().threadStepModifiedAt())
                                .puppy(GetStep1ThreadsResponsePuppy.builder()
                                        .puppyId(thread.puppy().puppyId())
                                        .customerId(thread.puppy().customerId())
                                        .name(thread.puppy().name())
                                        .breed(thread.puppy().breed())
                                        .weight(thread.puppy().weight())
                                        .sex(thread.puppy().sex())
                                        .age(thread.puppy().age())
                                        .birthdate(thread.puppy().birthdate())
                                        .profileImageUrl(thread.puppy().profileImageUrl())
                                        .puppySize(thread.puppy().puppySize())
                                        .diseases(thread.puppy().diseases())
                                        .build())
                                .build()
                        )
                        .toList()
        );
    }
}