package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetAllStep3AboveThreadsResult(
        List<BiddingThread.ThreadProfile> threads
) {

    public static GetAllStep3AboveThreadsResult from(List<BiddingThread.ThreadProfile> threads) {
        return new GetAllStep3AboveThreadsResult(threads);
    }
}
