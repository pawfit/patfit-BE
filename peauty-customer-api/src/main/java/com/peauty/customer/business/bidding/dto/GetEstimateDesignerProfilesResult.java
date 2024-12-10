package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record GetEstimateDesignerProfilesResult(
        BiddingProcess process,
        List<BiddingThread.Profile> threadProfiles
) {
    public static GetEstimateDesignerProfilesResult from(
            BiddingProcess process,
            List<BiddingThread.Profile> threadProfiles
    ) {
        return new GetEstimateDesignerProfilesResult(
                process,
                threadProfiles
        );
    }
}
