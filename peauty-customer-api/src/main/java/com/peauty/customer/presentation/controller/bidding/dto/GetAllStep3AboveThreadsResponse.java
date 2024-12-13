package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetAllStep3AboveThreadsResult;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetAllStep3AboveThreadsResponse(
        List<BiddingThread.Profile> threads
) {

    public static GetAllStep3AboveThreadsResponse from(GetAllStep3AboveThreadsResult result) {
        return new GetAllStep3AboveThreadsResponse(result.threads());
    }
}
