package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;
import com.peauty.designer.business.bidding.dto.SendEstimateCommand;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateProposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerBiddingServiceImpl implements DesignerBiddingService {

    private final DesignerBiddingPort designerBiddingPort;
    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;

    @Override
    public SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    ) {
        // 프로세스, 쓰레드 상태 변화 저장
        BiddingProcess biddingProcess = biddingProcessPort.getProcessById(processId);
        biddingProcess.responseEstimateThread(new BiddingThread.ID(threadId));
        biddingProcessPort.save(biddingProcess);

        // TODO 1. 프로세스, 쓰레드 상태 변화 후 견적서 저장하기
        //Estimate savedEstimate = designerBiddingPort.savedEstimate(
        //        userId, processId, threadId, command.toEstimate(new BiddingThread.ID(threadId)));
        // TODO 2. 견적 요청서 가져오기
        //EstimateProposal getEstimateProposal = estimateProposalPort.getEstimateProposalByProcessId(biddingProcess.getId());
        // SendEstimateResult.from(savedEstimate);
        return null;
    }

    @Override
    public CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    ) {
        // 프로세스, 쓰레드 상태 변화 저장
        BiddingProcess biddingProcess = biddingProcessPort.getProcessById(processId);
        biddingProcess.completeThread(new BiddingThread.ID(threadId));
        biddingProcessPort.save(biddingProcess);

        // TODO: 3. 미용 완료 후, 저장 할 데이터 저장
        return null;
    }
}
