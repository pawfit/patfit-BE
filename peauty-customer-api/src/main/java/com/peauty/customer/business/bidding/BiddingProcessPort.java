package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public interface BiddingProcessPort {

    BiddingProcess getProcessByProcessId(Long processId);
    BiddingProcess getProcessByPuppyId(Long puppyId);
    BiddingProcess getProcessByProcessIdAndPuppyId(Long processId, Long puppyId);
    BiddingProcess save(BiddingProcess process);
    void verifyNoProcessInProgress(Long puppyId);
    List<BiddingProcess> getAllProcessByCustomerId(Long customerId);
}
