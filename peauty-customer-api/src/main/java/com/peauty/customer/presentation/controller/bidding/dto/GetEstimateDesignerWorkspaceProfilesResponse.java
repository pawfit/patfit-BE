package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetEstimateDesignerWorkspaceProfilesResult;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetEstimateDesignerWorkspaceProfilesResponse(
        Long processId,
        String processStatus,
        String style,
        List<BiddingThread.Profile> workspaces
) {

    public static GetEstimateDesignerWorkspaceProfilesResponse from(GetEstimateDesignerWorkspaceProfilesResult result) {
        return new GetEstimateDesignerWorkspaceProfilesResponse(
                result.process().getSavedProcessId().value(),
                result.process().getStatus().getDescription(),
                result.estimateProposal().getSimpleGroomingStyle(),
                result.threadProfiles()
        );
    }
}
