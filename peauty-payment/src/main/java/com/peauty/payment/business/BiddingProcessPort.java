package com.peauty.payment.business;

import com.peauty.domain.bidding.BiddingProcess;

import java.util.List;

public interface BiddingProcessPort {

    BiddingProcess getProcessByProcessId(Long processId);
    BiddingProcess getProcessByPuppyId(Long puppyId);
    BiddingProcess getProcessByProcessIdAndPuppyId(Long processId, Long puppyId);
    BiddingProcess save(BiddingProcess process);
    List<BiddingProcess> getProcessesByDesignerId(Long designerId);
}
