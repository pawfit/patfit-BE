package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;

import java.time.format.DateTimeFormatter;

public record CompleteGroomingResponse(
        Long puppyId,
        Long processId,
        String processStatus,
        Long designerId,
        Long threadId,
        String threadStep,
        String threadStatus,
        String completedTime
) {

    public static CompleteGroomingResponse from(CompleteGroomingResult result) {
        return new CompleteGroomingResponse(
                result.puppyId().value(),
                result.processId().value(),
                result.processStatus().getDescription(),
                result.designerId().value(),
                result.threadId().value(),
                result.threadStep().getDescription(),
                result.threadStatus().getDescription(),
                result.processTimeInfo().getStatusModifiedAt().format(DateTimeFormatter.BASIC_ISO_DATE)
        );
    }
}
