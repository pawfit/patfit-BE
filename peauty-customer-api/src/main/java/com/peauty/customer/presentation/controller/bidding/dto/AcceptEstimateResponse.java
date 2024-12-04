package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;

public record AcceptEstimateResponse(
) {

    public static AcceptEstimateResponse from(AcceptEstimateResult result) {
        return new AcceptEstimateResponse(

        );
    }
}