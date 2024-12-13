package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetAllCompletedProcessResult(
        List<BiddingProcess.Profile> processes
) {

    public static GetAllCompletedProcessResult from(List<BiddingProcess.Profile> processes) {
        return new GetAllCompletedProcessResult(processes);
    }
}
