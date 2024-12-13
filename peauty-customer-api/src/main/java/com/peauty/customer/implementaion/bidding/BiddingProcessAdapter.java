package com.peauty.customer.implementaion.bidding;

import com.peauty.customer.business.bidding.BiddingProcessPort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingProcessStatus;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.mapper.BiddingMapper;
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

    private final BiddingProcessRepository processRepository;
    private final BiddingThreadRepository threadRepository;

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
    public void verifyNoProcessInProgress(Long customerId) {
        long count = processRepository.countByPuppyIdAndStatusIn(
                customerId,
                List.of(BiddingProcessStatus.RESERVED_YET, BiddingProcessStatus.RESERVED)
        );
        if (count > 0) {
            throw new PeautyException(PeautyResponseCode.PROCESS_ALREADY_IN_PROGRESS);
        }
    }

    @Override
    public List<BiddingProcess> getAllProcessByCustomerId(Long customerId) {
        return processRepository.findAllByUserId(customerId).stream()
                .map(processEntity -> {
                    List<BiddingThreadEntity> threads = threadRepository.findByBiddingProcessId(processEntity.getId());
                    return BiddingMapper.toProcessDomain(processEntity, threads);
                })
                .toList();
    }

    @Override
    public BiddingProcess getProcessByProcessId(Long processId) {
        BiddingProcessEntity foundProcessEntity = processRepository.findById(processId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
         List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
    }

    @Override
    public BiddingProcess getProcessByProcessIdAndPuppyId(Long processId, Long puppyId) {
        BiddingProcessEntity foundProcessEntity = processRepository.findByIdAndPuppyId(processId, puppyId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
        List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
    }

    @Override
    public BiddingProcess getProcessByPuppyId(Long puppyId) {
        BiddingProcessEntity foundProcessEntity = processRepository.findByPuppyId(puppyId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
        List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
    }
}