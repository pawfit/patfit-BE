package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.*;

public interface CustomerBiddingService {

    SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    );

    AcceptEstimateResult acceptEstimate(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    );

    GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    );

    GetEstimateDesignerProfilesResult getEstimateDesignerProfiles(
            Long userId,
            Long puppyId,
            Long processId
    );
}
