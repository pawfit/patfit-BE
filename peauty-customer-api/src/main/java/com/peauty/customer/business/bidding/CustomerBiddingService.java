package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;

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
}
