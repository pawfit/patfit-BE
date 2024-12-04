package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;
import com.peauty.designer.business.bidding.dto.SendEstimateCommand;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;
import com.peauty.designer.presentation.controller.bidding.dto.SendEstimateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignerBiddingServiceImpl implements DesignerBiddingService {

    private final DesignerBiddingPort designerBiddingPort;
    @Override
    public SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    ) {
        // TODO: 유효성 검사
        // 1. 올바른 프로세스인가?
        //  - 실제 DB에 존재하는가?
        //  - 프로세스 상태가 올바른 상태인가?
        Process process = designerBiddingPort.isValidProcess(processId);

        // 2. 올바른 쓰레드의 상태인가?

        // 3. 보내기
        Estimate savedEstimate = designerBiddingPort.sendEstimate(userId, processId, threadId, command.toEstimate());
        return SendEstimateResult.from(savedEstimate);
    }

    @Override
    public CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    ) {
        return null;
    }
}
