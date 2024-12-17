package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetOngoingProcessWithThreadsResult;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetOngoingProcessWithThreadsResponse(
        BiddingProcess.ProcessProfile process,
        List<BiddingThread.ThreadProfile> threads
) {

    public static GetOngoingProcessWithThreadsResponse from(GetOngoingProcessWithThreadsResult result) {
        if (result == null) {
            return new GetOngoingProcessWithThreadsResponse(null, null);
        }
        return new GetOngoingProcessWithThreadsResponse(result.process(), result.threads());
    }
}
