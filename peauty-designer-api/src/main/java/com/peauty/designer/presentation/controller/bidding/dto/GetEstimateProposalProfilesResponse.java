package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.GetEstimateProposalProfilesResult;
import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public record GetEstimateProposalProfilesResponse(
        List<BiddingProcess.Profile> estimateProposalProfile
) {

    public static GetEstimateProposalProfilesResponse from(GetEstimateProposalProfilesResult result) {
        return new GetEstimateProposalProfilesResponse(result.processProfiles());
    }
}
