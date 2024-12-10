package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.Estimate;

import java.util.Optional;

public interface EstimatePort {

    Estimate getEstimateByEstimateId(Long estimateId);
    Estimate getEstimateByThreadId(Long threadId);
    Optional<Estimate> findEstimateByThreadId(Long threadId);
}
