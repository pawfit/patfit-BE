package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetEstimateProposalProfilesResult(
        List<BiddingProcess.Profile> processProfiles
) {

    public static GetEstimateProposalProfilesResult from(List<BiddingProcess.Profile> processProfiles) {
        return new GetEstimateProposalProfilesResult(
                processProfiles
        );
    }
}
