package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.Estimate;

public interface EstimatePort {

    Estimate save(Estimate estimate);
    Estimate getById(Long estimateId);
}
