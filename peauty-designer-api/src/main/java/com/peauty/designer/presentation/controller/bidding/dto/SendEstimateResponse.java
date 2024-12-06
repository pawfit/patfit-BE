package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.SendEstimateResult;

public record SendEstimateResponse(
        Long estimateId,
        Long biddingThreadId,
        String content,
        String availableGroomingDate,
        String estimatedDuration,
        Long estimatedCost
) {

    public static SendEstimateResponse from(SendEstimateResult result) {
        return new SendEstimateResponse(
                result.estimateId().value(),
                result.biddingThreadId().value(),
                result.content(),
                result.availableGroomingDate(),
                result.estimatedDuration(),
                result.estimatedCost()
        );
    }
}
