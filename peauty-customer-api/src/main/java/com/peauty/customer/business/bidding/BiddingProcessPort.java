package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.BiddingProcess;

public interface BiddingProcessPort {

    BiddingProcess getProcessById(Long processId);
    BiddingProcess save(BiddingProcess process);
    BiddingProcess initProcess(BiddingProcess process);
}
