package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.Estimate;

public interface EstimatePort {

    Estimate getEstimateByEstimateId(Long estimateId);
    Estimate getEstimateByThreadId(Long threadId);
}
