package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.*;
import com.peauty.designer.business.puppy.PuppyPort;
import com.peauty.domain.bidding.*;
import com.peauty.domain.puppy.Puppy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerBiddingServiceImpl implements DesignerBiddingService {

    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;
    private final EstimatePort estimatePort;
    private final PuppyPort puppyPort;

    @Override
    @Transactional
    public SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    ) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
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
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        process.completeThread(new BiddingThread.ID(threadId));
        BiddingProcess savedProcess = biddingProcessPort.save(process);
        BiddingThread completedThread = savedProcess.getThread(new BiddingThread.ID(threadId));
        return CompleteGroomingResult.from(savedProcess, completedThread);
    }

    @Override
    public GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(Long userId, Long processId, Long threadId) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        EstimateProposal estimateProposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
        Optional<Estimate> estimate = estimatePort.findEstimateByThreadId(thread.getSavedThreadId().value());
        Puppy puppy = puppyPort.getPuppyByPuppyId(process.getPuppyId().value());
        return GetEstimateAndProposalDetailsResult.from(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate
        );
    }

    @Override
    public GetThreadsByStepResult getStep3AboveThreads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isAfter(BiddingThreadStep.ESTIMATE_RESPONSE))
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }

    @Override
    public GetThreadsByStepResult getStep2Threads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isEstimateResponse())
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }

    @Override
    public GetThreadsByStepResult getStep1Threads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isEstimateRequest())
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }
}