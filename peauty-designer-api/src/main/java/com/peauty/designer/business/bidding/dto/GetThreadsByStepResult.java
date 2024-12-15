package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetThreadsByStepResult(
        List<BiddingProcess.Profile> threads
) {

    public static GetThreadsByStepResult from(List<BiddingProcess.Profile> threads) {
        return new GetThreadsByStepResult(threads);
    }
}
