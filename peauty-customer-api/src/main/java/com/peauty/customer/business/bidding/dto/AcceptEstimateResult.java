package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.*;

public record AcceptEstimateResult(
        PuppyId puppyId,
        BiddingProcess.ID processId,
        BiddingProcessStatus processStatus,
        DesignerId designerId,
        BiddingThread.ID threadId,
        BiddingThreadStep threadStep,
        BiddingThreadStatus threadStatus,
        BiddingProcessTimeInfo processTimeInfo,
        BiddingThreadTimeInfo threadTimeInfo
) {

    public static AcceptEstimateResult from(BiddingProcess process, BiddingThread reservedThread) {
        return new AcceptEstimateResult(
                process.getPuppyId(),
                process.getSavedProcessId(),
                process.getStatus(),
                reservedThread.getDesignerId(),
                reservedThread.getSavedThreadId(),
                reservedThread.getStep(),
                reservedThread.getStatus(),
                process.getTimeInfo(),
                reservedThread.getTimeInfo()
        );
    }
}
