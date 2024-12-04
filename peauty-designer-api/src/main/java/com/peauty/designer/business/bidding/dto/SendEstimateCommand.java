package com.peauty.designer.business.bidding.dto;

import com.peauty.designer.business.bidding.Estimate;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SendEstimateCommand(
        String content,
        Integer cost,
        LocalDate date,
        String proposalImageUrl

) {
    public Estimate toEstimate() {
        return new Estimate();
    }
}
