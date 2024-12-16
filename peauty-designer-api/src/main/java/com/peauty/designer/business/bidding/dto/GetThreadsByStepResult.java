package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetThreadsByStepResult(
        List<BiddingProcess.ProcessProfile> threads
) {

    public static GetThreadsByStepResult from(List<BiddingProcess.ProcessProfile> threads) {
        return new GetThreadsByStepResult(threads);
    }
}
