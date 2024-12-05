package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.EstimateProposal;

public interface EstimateProposalPort {

    EstimateProposal getProposalById(Long proposalId);
}
