package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetEstimateAndProposalDetailsResult;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.puppy.Puppy;

public record GetEstimateAndProposalDetailsResponse(
        Long processId,
        String processStatus,
        Long threadId,
        String threadStatus,
        String threadStep,
        Puppy.Profile puppyProfile,
        EstimateProposal.Profile estimateProposal,
        Estimate.Profile estimate
) {

    public static GetEstimateAndProposalDetailsResponse from(GetEstimateAndProposalDetailsResult result) {
        return new GetEstimateAndProposalDetailsResponse(
                result.process().getSavedProcessId().value(),
                result.process().getStatus().getDescription(),
                result.thread().getSavedThreadId().value(),
                result.thread().getStep().getDescription(),
                result.thread().getStatus().getDescription(),
                result.puppy().getProfile(),
                result.estimateProposal().getProfile(),
                result.estimate().getProfile()
        );
    }
}
