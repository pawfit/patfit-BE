package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetEstimateProposalDetailResult;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.puppy.Puppy;

public record GetEstimateProposalDetailResponse(
        Long processId,
        String processStatus,
        Puppy.PuppyProfile puppy,
        EstimateProposal.EstimateProposalProfile estimateProposal

) {

    public static GetEstimateProposalDetailResponse from(GetEstimateProposalDetailResult result) {
        return new GetEstimateProposalDetailResponse(
                result.process().getSavedProcessId().value(),
                result.process().getStatus().getDescription(),
                result.puppy().getProfile(),
                result.estimateProposal().getProfile()
        );
    }
}
