package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.*;

public record CompleteGroomingResult(
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

    public static CompleteGroomingResult from(BiddingProcess savedProcess, BiddingThread completedThread) {
        return new CompleteGroomingResult(
                savedProcess.getPuppyId(),
                savedProcess.getSavedProcessId(),
                savedProcess.getStatus(),
                completedThread.getDesignerId(),
                completedThread.getSavedThreadId(),
                completedThread.getStep(),
                completedThread.getStatus(),
                savedProcess.getTimeInfo(),
                completedThread.getTimeInfo()
        );
    }
}