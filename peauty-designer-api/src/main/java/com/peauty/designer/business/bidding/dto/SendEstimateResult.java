package com.peauty.designer.business.bidding.dto;


import com.peauty.domain.bidding.Estimate;
import lombok.Builder;

@Builder
public record SendEstimateResult() {
    public static SendEstimateResult from(Estimate savedEstimate) {
        return null;
    }
}
