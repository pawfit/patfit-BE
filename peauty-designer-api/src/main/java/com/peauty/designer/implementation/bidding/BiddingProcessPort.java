package com.peauty.designer.implementation.bidding;

import com.peauty.domain.bidding.BiddingProcess;

public interface BiddingProcessPort {

    BiddingProcess getProcessById(Long processId);
    BiddingProcess save(BiddingProcess process);
    BiddingProcess initProcess(BiddingProcess process);
}
