package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.EstimateProposal;

public interface EstimateProposalPort {

    EstimateProposal getProposalById(Long proposalId);
    EstimateProposal save(EstimateProposal proposal);
}
