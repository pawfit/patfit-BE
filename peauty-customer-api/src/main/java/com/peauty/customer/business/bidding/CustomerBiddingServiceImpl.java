package com.peauty.customer.business.bidding;

import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.DesignerId;
import com.peauty.domain.bidding.PuppyId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerBiddingServiceImpl implements CustomerBiddingService {

    private final BiddingProcessPort biddingProcessPort;

    // TODO 프로세스 접근 검증 ex) 올바른 유저인지...
    // TODO 견적요청서 저장
    @Override
    @Transactional
    public SendEstimateProposalResult sendEstimateProposal(
            Long userId,
            Long puppyId,
            SendEstimateProposalCommand command
    ) {
        BiddingProcess process = biddingProcessPort.initProcess(BiddingProcess.createNewProcess(new PuppyId(puppyId)));
        command.designerIds()
                .forEach(id -> process.addNewThread(new DesignerId(id)));
        BiddingProcess savedProcess = biddingProcessPort.save(process);
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
