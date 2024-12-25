package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.Estimate;

import java.util.Optional;

public interface EstimatePort {

    Estimate save(Estimate estimate);
    Estimate getEstimateByEstimateId(Long estimateId);
    Estimate getEstimateByThreadId(Long threadId);
    Optional<Estimate> findEstimateByThreadId(Long threadId);
}
