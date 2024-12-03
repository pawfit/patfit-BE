package com.peauty.customer.business.bidding.dto;

import java.util.List;

public record SendEstimateProposalCommand(
        List<Long> designerIds
) {
}
