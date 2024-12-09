package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetEstimateDesignerProfilesResult;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetEstimateDesignerProfilesResponse(
        Long processId,
        String processStatus,
        List<BiddingThread.Profile> estimateDesigners
) {

    public static GetEstimateDesignerProfilesResponse from(GetEstimateDesignerProfilesResult result) {
        return new GetEstimateDesignerProfilesResponse(
                result.process().getSavedProcessId().value(),
                result.process().getStatus().getDescription(),
                result.threadProfiles()
        );
    }
}
