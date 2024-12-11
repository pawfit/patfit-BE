package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.EstimateProposal;

import java.util.List;

public record GetEstimateDesignerWorkspaceProfilesResult(
        BiddingProcess process,
        EstimateProposal estimateProposal,
        List<BiddingThread.Profile> threadProfiles
) {
    public static GetEstimateDesignerWorkspaceProfilesResult from(
            BiddingProcess process,
            EstimateProposal estimateProposal,
            List<BiddingThread.Profile> threadProfiles
    ) {
        return new GetEstimateDesignerWorkspaceProfilesResult(
                process,
                estimateProposal,
                threadProfiles
        );
    }
}
