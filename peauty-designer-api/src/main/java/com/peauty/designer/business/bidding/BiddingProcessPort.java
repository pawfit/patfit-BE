package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingProcessStatus;

public interface BiddingProcessPort {

    BiddingProcess getProcessById(Long processId);
    BiddingProcess save(BiddingProcess process);
    void isValidStatus(BiddingProcessStatus status);
}
