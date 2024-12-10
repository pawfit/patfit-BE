package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.puppy.Puppy;

import java.util.Optional;

public record GetEstimateAndProposalDetailsResult(
        BiddingProcess process,
        BiddingThread thread,
        Puppy puppy,
        EstimateProposal estimateProposal,
        Optional<Estimate> estimate
) {

    public static GetEstimateAndProposalDetailsResult from(
            BiddingProcess process,
            BiddingThread thread,
            Puppy puppy,
            EstimateProposal estimateProposal,
            Optional<Estimate> estimate
    ) {
        return new GetEstimateAndProposalDetailsResult(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate
        );
    }
}
