package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;
import com.peauty.domain.bidding.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerBiddingServiceImpl implements CustomerBiddingService {

    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;

    // TODO 프로세스 접근 검증 ex) 올바른 유저인지...
    @Override
    @Transactional
    public SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    ) {
        BiddingProcess newProcess = BiddingProcess.createNewProcess(new PuppyId(puppyId));
        command.designerIds().forEach(id -> newProcess.addNewThread(new DesignerId(id)));
        BiddingProcess savedProcess = biddingProcessPort.save(newProcess);
        EstimateProposal newProposal = command.toEstimateProposal(savedProcess.getSavedProcessId());
        estimateProposalPort.save(newProposal);;
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
        return null;
    }
}
