package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetThreadsByStepResult;
import com.peauty.domain.puppy.Puppy;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record GetThreadsByStepResponse(
        List<Thread> threads
) {

    @Builder
    public record Thread(
            Long processId,
            String processStatus,
            LocalDateTime processCreatedAt,
            LocalDateTime processStatusModifiedAt,
            Long threadId,
            String threadStep,
            String threadStatus,
            LocalDateTime threadCreatedAt,
            LocalDateTime threadStepModifiedAt,
            Puppy.PuppyProfile puppy
    ) {
    }

    public static GetThreadsByStepResponse from(GetThreadsByStepResult result) {
        return new GetThreadsByStepResponse(
                result.threads().stream()
                        .map(thread -> Thread.builder()
                                .processId(thread.processId())
                                .processStatus(thread.processStatus())
                                .processCreatedAt(thread.processCreatedAt())
                                .processStatusModifiedAt(thread.processStatusModifiedAt())
                                .threadId(thread.threadInfo().threadId())
                                .threadStep(thread.threadInfo().threadStep())
                                .threadStatus(thread.threadInfo().threadStatus())
                                .threadCreatedAt(thread.threadInfo().threadCreatedAt())
                                .threadStepModifiedAt(thread.threadInfo().threadStepModifiedAt())
                                .puppy(thread.puppy())
                                .build()
                        )
                        .toList()
        );
    }
}
