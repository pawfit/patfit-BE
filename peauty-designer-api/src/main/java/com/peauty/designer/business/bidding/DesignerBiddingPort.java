package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.Estimate;

public interface DesignerBiddingPort {
    Estimate savedEstimate(
            Long userId,
            Long processId,
            Long threadId,
            Estimate estimate);
}
