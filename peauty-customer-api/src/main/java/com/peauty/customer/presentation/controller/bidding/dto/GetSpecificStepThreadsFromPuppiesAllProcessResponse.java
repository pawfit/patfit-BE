package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetSpecificStepThreadsFromPuppiesAllProcessResult;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetSpecificStepThreadsFromPuppiesAllProcessResponse(
        List<BiddingThread.Profile> threads
) {

    public static GetSpecificStepThreadsFromPuppiesAllProcessResponse from(GetSpecificStepThreadsFromPuppiesAllProcessResult result) {
        return new GetSpecificStepThreadsFromPuppiesAllProcessResponse(result.threads());
    }
}
