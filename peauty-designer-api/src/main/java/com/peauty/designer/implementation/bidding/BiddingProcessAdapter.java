package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.BiddingProcessPort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingProcessStatus;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.process.BiddingProcessRepository;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BiddingProcessAdapter implements BiddingProcessPort {

    private final BiddingProcessRepository processRepository;
    private final BiddingThreadRepository threadRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public BiddingProcess save(BiddingProcess process) {
        BiddingProcessEntity savedProcessEntity = processRepository.save(
                BiddingMapper.toProcessEntity(process)
        );
        List<BiddingThreadEntity> threadEntities = process.getThreads().stream()
                .map(thread -> BiddingMapper.toThreadEntity(thread, savedProcessEntity))
                .toList();
        List<BiddingThreadEntity> savedThreads = threadRepository.saveAll(threadEntities);
        return BiddingMapper.toProcessDomain(savedProcessEntity, savedThreads);
    }

    @Override
    public void isValidStatus(BiddingProcessStatus status) {
        if(status.isCanceled()){
            throw new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);
        }
        if(status.isCompleted()){
            throw new PeautyException(PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);
        }
    }

    @Override
    public BiddingProcess getProcessById(Long processId) {
        BiddingProcessEntity foundProcessEntity = processRepository.findById(processId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
        List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
    }
}
