package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;

import java.util.List;

public record SendEstimateProposalRequest(
        List<Long> designerIds
) {
    public SendEstimateProposalCommand toCommand() {
        return new SendEstimateProposalCommand(designerIds);
    }
}
