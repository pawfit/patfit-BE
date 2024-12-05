package com.peauty.designer.business.bidding.dto;


import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import lombok.Builder;

@Builder
public record SendEstimateResult(
        Estimate.ID estimateId,
        BiddingThread.ID biddingThreadId,
        String content,
        String availableGroomingDate,
        String estimatedDuration,
        Long estimatedCost
) {

    public static SendEstimateResult from(Estimate estimate) {
        return new SendEstimateResult(
                estimate.getSavedEstimateId(),
                estimate.getThreadId(),
                estimate.getContent(),
                estimate.getAvailableGroomingDate(),
                estimate.getEstimatedDuration(),
                estimate.getEstimatedCost()
        );
    }
}