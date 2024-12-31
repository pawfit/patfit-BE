package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetOngoingProcessWithThreadsResult(
        BiddingProcess.ProcessProfile process,
        List<BiddingThread.ThreadProfile> threads
) {

    public static GetOngoingProcessWithThreadsResult from(
            BiddingProcess.ProcessProfile process,
            List<BiddingThread.ThreadProfile> threads
    ) {
        return new GetOngoingProcessWithThreadsResult(process, threads);
    }
}
