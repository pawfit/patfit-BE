package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.puppy.Puppy;

public record GetEstimateProposalDetailResult(
        Puppy puppy,
        BiddingProcess process,
        EstimateProposal estimateProposal
) {

    public static GetEstimateProposalDetailResult from(
            Puppy puppy,
            BiddingProcess process,
            EstimateProposal estimateProposal
    ) {
        return new GetEstimateProposalDetailResult(
                puppy,
                process,
                estimateProposal
        );
    }
}
