package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;
import com.peauty.designer.business.bidding.dto.SendEstimateCommand;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;

public interface DesignerBiddingService {

    SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    );

    CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    );
}
