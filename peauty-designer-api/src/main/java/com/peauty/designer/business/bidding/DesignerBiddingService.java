package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.*;

public interface DesignerBiddingService {

    SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    );

    CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    );

    GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(
            Long userId,
            Long processId,
            Long threadId
    );

    GetEstimateProposalProfilesResult getEstimateProposalProfiles(Long userId);
}
