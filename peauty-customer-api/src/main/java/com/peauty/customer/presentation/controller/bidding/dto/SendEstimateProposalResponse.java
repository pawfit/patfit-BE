package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record SendEstimateProposalResponse(
        Long processId,
        Long puppyId,
        List<Long> designerIds,
        String processStatus,
        String createdAt
) {

    public static SendEstimateProposalResponse from(SendEstimateProposalResult result) {
        return new SendEstimateProposalResponse(
                result.processId(),
                result.puppyId(),
                result.designerIds(),
                result.processStatus().getDescription(),
                result.processTimeInfo().getCreatedAt().format(DateTimeFormatter.BASIC_ISO_DATE)
        );
    }
}
