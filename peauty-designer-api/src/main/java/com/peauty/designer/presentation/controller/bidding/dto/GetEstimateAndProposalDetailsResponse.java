package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetEstimateAndProposalDetailsResult;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.puppy.Puppy;

public record GetEstimateAndProposalDetailsResponse(
        Long processId,
        String processStatus,
        Long threadId,
        String threadStatus,
        String threadStep,
        Puppy.Profile puppyProfile, // TODO puppy 로 바꾸기, 프론트 연동 작업 중이라 추후에 적용
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
                result.estimate().map(Estimate::getProfile).orElse(null)
        );
    }
}
