package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.customer.business.review.ReviewPort;
import com.peauty.domain.bidding.*;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.puppy.Puppy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomerBiddingServiceImpl implements CustomerBiddingService {

    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;
    private final EstimatePort estimatePort;
    private final PuppyPort puppyPort;
    private final DesignerPort designerPort;
    private final ReviewPort reviewPort;

    @Override
    public GetPuppyProfilesWithCanStartProcessStatusResult getPuppyProfilesWithCanStartProcessStatus(Long userId) {
        return GetPuppyProfilesWithCanStartProcessStatusResult.from(
                puppyPort.getAllPuppiesByCustomerId(userId).stream()
                        .map(puppy -> puppy.getProfile(biddingProcessPort.hasPuppyOngoingProcess(puppy.getPuppyId())))
                        .toList()
        );
    }

    @Override
    @Transactional
    public SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    ) {
        puppyPort.verifyPuppyOwnership(puppyId, userId);
        biddingProcessPort.verifyNoProcessInProgress(puppyId);
        designerPort.checkExistsDesignersByDesignerIds(command.designerIds());
        BiddingProcess newProcess = BiddingProcess.createNewProcess(new PuppyId(puppyId));
        command.designerIds().forEach(id -> newProcess.addNewThread(new DesignerId(id)));
        BiddingProcess savedProcess = biddingProcessPort.save(newProcess);
        EstimateProposal newProposal = command.toEstimateProposal(savedProcess.getSavedProcessId());
        estimateProposalPort.save(newProposal);
        return SendEstimateProposalResult.from(savedProcess);
    }

    @Override
    @Transactional
    public AcceptEstimateResult acceptEstimate(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    ) {
        puppyPort.verifyPuppyOwnership(puppyId, userId);
        BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppyId);
        process.reserveThread(new BiddingThread.ID(threadId));
        BiddingProcess savedProcess = biddingProcessPort.save(process);
        BiddingThread reservedThread = savedProcess.getThread(new BiddingThread.ID(threadId));
        return AcceptEstimateResult.from(savedProcess, reservedThread);
    }

    @Override
    public GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(
            Long userId,
            Long puppyId,
            Long processId,
            Long threadId
    ) {
        Puppy puppy = puppyPort.getPuppyByCustomerIdAndPuppyId(userId, puppyId);
        BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppy.getPuppyId());
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        EstimateProposal estimateProposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
        Estimate estimate = estimatePort.getEstimateByThreadId(thread.getSavedThreadId().value());
        Designer.DesignerProfile designerProfile = designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value());
        return GetEstimateAndProposalDetailsResult.from(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate,
                designerProfile
        );
    }

    @Override
    public GetEstimateProposalDetailResult getEstimateProposalDetail(
            Long userId,
            Long puppyId,
            Long processId
    ) {
        Puppy puppy = puppyPort.getPuppyByCustomerIdAndPuppyId(userId, puppyId);
        BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppy.getPuppyId());
        EstimateProposal estimateProposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
        return GetEstimateProposalDetailResult.from(puppy, process, estimateProposal);
    }

    @Override
    public GetAllStep3AboveThreadsResult getAllStep3AboveThreads(
            Long userId,
            Long puppyId
    ) {
        return GetAllStep3AboveThreadsResult.from(
                biddingProcessPort.getProcessesByPuppyId(puppyId).stream()
                        .flatMap(process -> process.getThreads().stream()
                                .filter(thread -> thread.getStep().isAfter(BiddingThreadStep.ESTIMATE_RESPONSE))
                                .map(thread -> thread.getProfile(
                                        designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value()),
                                        estimatePort.getEstimateByThreadId(thread.getSavedThreadId().value()).getProfile(),
                                        estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getSimpleGroomingStyle()
                                )))
                        .toList()
        );
    }

    @Override
    public GetOngoingProcessWithThreadsResult getOngoingProcessWithStep1Threads(
            Long userId,
            Long puppyId
    ) {
        return biddingProcessPort.findOngoingProcessByPuppyId(puppyId)
                .map(process -> GetOngoingProcessWithThreadsResult.from(
                        process.getProfile(estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile()),
                        process.getThreads().stream()
                                .filter(thread -> thread.getStep().isEstimateRequest())
                                .map(thread -> thread.getProfile(designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value())))
                                .toList()
                ))
                .orElse(null);
    }

    @Override
    public GetOngoingProcessWithThreadsResult getOngoingProcessWithStep2Threads(
            Long userId,
            Long puppyId
    ) {
        return biddingProcessPort.findOngoingProcessByPuppyId(puppyId)
                .map(process -> GetOngoingProcessWithThreadsResult.from(
                        process.getProfile(estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile()),
                        process.getThreads().stream()
                                .filter(thread -> thread.getStep().isEstimateResponse())
                                .map(thread -> thread.getProfile(
                                        designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value()),
                                        estimatePort.getEstimateByThreadId(thread.getSavedThreadId().value()).getProfile()
                                ))
                                .toList()
                ))
                .orElse(null);
    }

    @Override
    public GetAllStep3AboveThreadsResult getCanReviewThreads(Long userId) {
        return GetAllStep3AboveThreadsResult.from(
                puppyPort.getAllPuppiesByCustomerId(userId).stream()
                        .flatMap(puppy -> biddingProcessPort.getProcessesByPuppyId(puppy.getPuppyId()).stream()
                                .map(process -> new ProcessWithPuppy(process, puppy.getPuppyId())))
                        .flatMap(processWithPuppy -> processWithPuppy.process().getThreads().stream()
                                .filter(thread -> thread.getStep().isCompleted())
                                .filter(thread -> !reviewPort.existsByBiddingThreadId(thread.getSavedThreadId().value()))
                                .map(thread -> thread.getProfile(
                                        processWithPuppy.puppyId(),
                                        designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value()),
                                        estimatePort.getEstimateByThreadId(thread.getSavedThreadId().value()).getProfile(),
                                        estimateProposalPort.getProposalByProcessId(processWithPuppy.process().getSavedProcessId().value()).getSimpleGroomingStyle()
                                )))
                        .toList()
        );
    }

    private record ProcessWithPuppy(BiddingProcess process, Long puppyId) {}
}
