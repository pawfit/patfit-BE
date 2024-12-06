package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;
import com.peauty.designer.business.bidding.dto.SendEstimateCommand;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;
import com.peauty.designer.presentation.controller.bidding.dto.CompleteGroomingResponse;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerBiddingServiceImpl implements DesignerBiddingService {

    private final EstimatePort estimatePort;
    private final BiddingProcessPort biddingProcessPort;

    @Override
    @Transactional
    public SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    ) {
        BiddingProcess process = biddingProcessPort.getProcessById(processId);
        process.responseEstimateThread(new BiddingThread.ID(threadId));
        BiddingThread responseEstimateThread = biddingProcessPort.save(process)
                .getThread(new BiddingThread.ID(threadId));
        Estimate newEstimate = command.toEstimate(responseEstimateThread.getSavedThreadId().value());
        Estimate savedEstimate = estimatePort.save(newEstimate);
        return SendEstimateResult.from(savedEstimate);
    }

    @Override
    @Transactional
    public CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    ) {
        BiddingProcess process = biddingProcessPort.getProcessById(processId);
        process.completeThread(new BiddingThread.ID(threadId));
        BiddingProcess savedProcess = biddingProcessPort.save(process);
        BiddingThread completedThread = savedProcess.getThread(new BiddingThread.ID(threadId));
        return CompleteGroomingResult.from(savedProcess, completedThread);
    }
}
