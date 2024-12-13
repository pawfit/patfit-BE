package com.peauty.customer.business.bidding;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;
import java.util.Optional;

public interface BiddingProcessPort {

    BiddingProcess getProcessByProcessId(Long processId);
    List<BiddingProcess> getProcessesByPuppyId(Long puppyId);
    Optional<BiddingProcess> findOngoingProcessByPuppyId(Long puppyId);
    BiddingProcess getProcessByProcessIdAndPuppyId(Long processId, Long puppyId);
    BiddingProcess save(BiddingProcess process);
    void verifyNoProcessInProgress(Long puppyId);
}
