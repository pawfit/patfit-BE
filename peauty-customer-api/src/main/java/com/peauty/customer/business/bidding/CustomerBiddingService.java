package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;
import com.peauty.customer.business.bidding.dto.GetEstimateAndProposalDetailsResult;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;
import org.springframework.web.bind.annotation.PathVariable;

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
}
