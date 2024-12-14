package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetOngoingProcessWithThreadsResult(
        BiddingProcess.Profile process,
        List<BiddingThread.Profile> threads
) {

    public static GetOngoingProcessWithThreadsResult from(
            BiddingProcess.Profile process,
            List<BiddingThread.Profile> threads
    ) {
        return new GetOngoingProcessWithThreadsResult(process, threads);
    }
}
