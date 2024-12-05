package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.EstimateProposal;

import java.util.Optional;

public interface EstimateProposalPort {

    EstimateProposal save(EstimateProposal proposal);

    EstimateProposal getEstimateProposalByProcessId(Optional<BiddingProcess.ID> id);
}
