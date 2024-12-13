package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetAllStep3AboveThreadsResult(
        List<BiddingThread.Profile> threads
) {

    public static GetAllStep3AboveThreadsResult from(List<BiddingThread.Profile> threads) {
        return new GetAllStep3AboveThreadsResult(threads);
    }
}
