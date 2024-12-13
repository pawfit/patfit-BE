package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetAllCompletedProcessResult;
import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetAllCompletedProcess(

        List<BiddingProcess.Profile> processes
) {

    public static GetAllCompletedProcess from(GetAllCompletedProcessResult result) {
        return new GetAllCompletedProcess(result.processes());
    }
}
