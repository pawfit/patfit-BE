package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.SendEstimateResult;

public record SendEstimateResponse() {
    public static SendEstimateResponse from(SendEstimateResult result) {
        return new SendEstimateResponse(

        );
    }
}
