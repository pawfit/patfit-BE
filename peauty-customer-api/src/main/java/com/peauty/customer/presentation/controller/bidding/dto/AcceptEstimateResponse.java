package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;

import java.time.format.DateTimeFormatter;

public record AcceptEstimateResponse(
        Long puppyId,
        Long processId,
        String processStatus,
        Long designerId,
        Long threadId,
        String threadStep,
        String threadStatus,
        String acceptedTime
) {

    public static AcceptEstimateResponse from(AcceptEstimateResult result) {
        return new AcceptEstimateResponse(
                result.puppyId().value(),
                result.processId().value(),
                result.processStatus().getDescription(),
                result.threadId().value(),
                result.designerId().value(),
                result.threadStep().getDescription(),
                result.threadStatus().getDescription(),
                result.processTimeInfo().getStatusModifiedAt().format(DateTimeFormatter.BASIC_ISO_DATE)
        );
    }
}