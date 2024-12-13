package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetSpecificStepThreadsFromPuppiesAllProcessResult(
        List<BiddingThread.Profile> threads
) {

    public static GetSpecificStepThreadsFromPuppiesAllProcessResult from(List<BiddingThread.Profile> threads) {
        return new GetSpecificStepThreadsFromPuppiesAllProcessResult(threads);
    }
}
