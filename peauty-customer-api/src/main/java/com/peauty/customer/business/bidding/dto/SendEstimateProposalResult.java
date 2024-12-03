package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingProcessStatus;
import com.peauty.domain.bidding.BiddingProcessTimeInfo;
import com.peauty.domain.bidding.BiddingThread;

import java.util.List;

public record SendEstimateProposalResult(
        Long processId,
        Long puppyId,
        List<Long> designerIds,
        BiddingProcessStatus processStatus,
        BiddingProcessTimeInfo processTimeInfo
) {

    public static SendEstimateProposalResult from(BiddingProcess process) {
        return new SendEstimateProposalResult(
                process.getId().orElse(new BiddingProcess.ID(0L)).value(),
                process.getPuppyId().value(),
                process.getThreads().stream()
                        .map(thread -> thread.getDesignerId().value())
                        .toList(),
                process.getStatus(),
                process.getTimeInfo()
        );
    }
}
