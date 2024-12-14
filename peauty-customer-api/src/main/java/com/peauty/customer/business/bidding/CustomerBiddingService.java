package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.*;

public interface CustomerBiddingService {

    GetPuppyProfilesWithCanStartProcessStatusResult getPuppyProfilesWithCanStartProcessStatus(Long userId);

    SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    );

    AcceptEstimateResult acceptEstimate(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    );

    GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    );

    GetEstimateProposalDetailResult getEstimateProposalDetail(
            Long userId,
            Long puppyId,
            Long processId
    );

    GetAllStep3AboveThreadsResult getAllStep3AboveThreads(
            Long userId,
            Long puppyId
    );

    GetOngoingProcessWithThreadsResult getOngoingProcessWithStep1Threads(
            Long userId,
            Long puppyId
    );

    GetOngoingProcessWithThreadsResult getOngoingProcessWithStep2Threads(
            Long userId,
            Long puppyId
    );
}
