package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.domain.bidding.GroomingType;

import java.time.LocalDateTime;
import java.util.List;

public record SendEstimateProposalRequest(
        List<Long> designerIds,
        GroomingType groomingType,
        String detail,
        List<String> imageUrls,
        Long desiredCost,
        LocalDateTime desiredDateTime,
        String body,
        String face
) {
    public SendEstimateProposalCommand toCommand() {
        return new SendEstimateProposalCommand(
                designerIds,
                groomingType,
                detail,
                imageUrls,
                desiredCost,
                desiredDateTime,
                body,
                face
        );
    }
}
