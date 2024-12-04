package com.peauty.designer.business.bidding;

public interface DesignerBiddingPort {
    Estimate sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            Estimate estimate);

    Process isValidProcess(Long processId);
}
