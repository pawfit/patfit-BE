package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.EstimateProposal;

public interface EstimateProposalPort {

    EstimateProposal getProposalByProposalId(Long proposalId);
    EstimateProposal getProposalByProcessId(Long processId);
    EstimateProposal save(EstimateProposal proposal);
}
