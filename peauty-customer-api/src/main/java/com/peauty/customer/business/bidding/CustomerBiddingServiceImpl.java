package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.domain.bidding.*;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.puppy.Puppy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerBiddingServiceImpl implements CustomerBiddingService {

    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;
    private final EstimatePort estimatePort;
    private final PuppyPort puppyPort;
    private final DesignerPort designerPort;

    @Override
    @Transactional
    public SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    ) {
        puppyPort.verifyPuppyOwnership(puppyId, userId);
        biddingProcessPort.verifyNoProcessInProgress(puppyId);
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
        // TODO 미용사 프로필도 추가할 수 있음
        return GetEstimateAndProposalDetailsResult.from(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate
        );
    }

    @Override
    public GetEstimateDesignerProfilesResult getEstimateDesignerProfiles(
            Long userId,
            Long puppyId,
            Long processId
    ) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppyId);
        List<BiddingThread.Profile> threadProfiles = process.getThreads().stream()
                .map(thread -> {
                    Designer.Profile designerProfile = designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value());
                    return thread.getProfile(designerProfile);
                }).toList();
        return GetEstimateDesignerProfilesResult.from(process, threadProfiles);
    }
}
