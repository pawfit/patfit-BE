package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.BiddingThreadPort;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BiddingThreadAdapter implements BiddingThreadPort {

    private final BiddingThreadRepository threadRepository;


    // TODO 사용하지 않을 듯 -> 삭제 요망
    @Override
    public BiddingThread getThreadById(Long threadId) {
        BiddingThreadEntity biddingThreadEntity = threadRepository.findById(threadId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
        return BiddingMapper.toThreadDomain(biddingThreadEntity);
    }

//    public BiddingProcess getProcessById(Long processId) {
//        BiddingProcessEntity foundProcessEntity = processRepository.findById(processId)
//                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
//        List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
//        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
//    }
}
