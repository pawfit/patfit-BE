package com.peauty.customer.implementaion.bidding;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.process.BiddingProcessRepository;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BiddingProcessAdapter implements BiddingProcessPort {

    private final BiddingProcessRepository biddingProcessRepository;
    private final BiddingThreadRepository biddingThreadRepository;

    @Override
    public BiddingProcess getProcessById(Long processId) {
        BiddingProcessEntity foundProcessEntity = biddingProcessRepository.findById(processId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
        List<BiddingThreadEntity> foundBiddingThreadEntities = biddingThreadRepository.findByBiddingProcessId(processId);
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundBiddingThreadEntities);
    }

    @Override
    public BiddingProcess save(BiddingProcess process) {
        checkInitProcess(process);
        BiddingProcessEntity processEntityToSave = BiddingMapper.toProcessEntity(process);
        List<BiddingThreadEntity> threadEntitiesToSave = BiddingMapper.toThreadEntities(process.getThreads());
        BiddingProcessEntity savedProcessEntity = biddingProcessRepository.save(processEntityToSave);
        List<BiddingThreadEntity> savedThreadEntities = biddingThreadRepository.saveAll(threadEntitiesToSave);
        return BiddingMapper.toProcessDomain(savedProcessEntity, savedThreadEntities);
    }

    @Override
    public BiddingProcess initProcess(BiddingProcess process) {
        BiddingProcessEntity processEntityToSave = BiddingMapper.toProcessEntity(process);
        BiddingProcessEntity savedProcessEntity = biddingProcessRepository.save(processEntityToSave);
        return BiddingMapper.toProcessDomain(savedProcessEntity, List.of());
    }

    // TODO init 을 강제하기 위함인데.. 다른 방법이 있나 알아보기
    private void checkInitProcess(BiddingProcess process) {
        process.getId().orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_INITIALIZED_PROCESS));
    }
}
