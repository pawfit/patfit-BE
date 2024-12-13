package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.customer.business.review.ReviewPort;
import com.peauty.domain.bidding.*;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Designer.Profile designerProfile = designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value());
        return GetEstimateAndProposalDetailsResult.from(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate,
                designerProfile
        );
    }

    // TODO 쿼리 dsl 을 이용한 효율적인 쿼리 도입
    @Override
    public GetEstimateDesignerWorkspaceProfilesResult getEstimateDesignerWorkspaceProfiles(
            Long userId,
            Long puppyId,
            Long processId
    ) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppyId);
        EstimateProposal estimateProposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
        List<BiddingThread.Profile> threadProfiles = process.getThreads().stream()
                .map(thread -> thread.getProfile(
                        designerPort.getDesignerProfileByDesignerId(thread.getDesignerId().value()),
                        estimatePort.findEstimateByThreadId(thread.getSavedThreadId().value())
                                .map(Estimate::getProfile)
                                .orElse(null)
                )).toList();
        return GetEstimateDesignerWorkspaceProfilesResult.from(process, estimateProposal, threadProfiles);
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
    public GetAllCompletedProcessResult getAllCompletedProcess(Long userId) {
        return new GetAllCompletedProcessResult(
                biddingProcessPort.getAllProcessByCustomerId(userId).stream()
                        .filter(process -> process.getStatus().isCompleted())
                        .map(process -> {
                                    BiddingThread completedThread = process.getThreads().stream()
                                            .filter(thread -> thread.getStep().isCompleted())
                                            .findFirst()
                                            // TODO 프로세스가 완료인데 완료가 되지 않는 스레드는 없다... 해당 예외 고민하기
                                            .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_YET_IMPLEMENTED));
                                    boolean isReviewed = reviewPort.existsByBiddingThreadId(completedThread.getSavedThreadId().value());
                                    return process.getProfile(
                                            puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                            estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                            designerPort.getDesignerProfileByDesignerId(completedThread.getDesignerId().value()),
                                            isReviewed
                                    );
                                }
                        ).toList());
    }
}
