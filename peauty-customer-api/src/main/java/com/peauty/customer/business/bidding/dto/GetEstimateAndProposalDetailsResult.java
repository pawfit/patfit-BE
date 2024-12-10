package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.puppy.Puppy;

public record GetEstimateAndProposalDetailsResult(
        BiddingProcess process,
        BiddingThread thread,
        Puppy puppy,
        EstimateProposal estimateProposal,
        Estimate estimate,
        Designer.Profile designer
) {

    public static GetEstimateAndProposalDetailsResult from(
            BiddingProcess process,
            BiddingThread thread,
            Puppy puppy,
            EstimateProposal estimateProposal,
            Estimate estimate,
            Designer.Profile designerProfile
    ) {
        return new GetEstimateAndProposalDetailsResult(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate,
                designerProfile
        );
    }
}
