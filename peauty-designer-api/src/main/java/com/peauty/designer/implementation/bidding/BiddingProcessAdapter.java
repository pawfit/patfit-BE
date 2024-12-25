package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.BiddingProcessPort;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.mapper.BiddingMapper;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.process.BiddingProcessRepository;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
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
    public List<BiddingProcess> getProcessesByDesignerId(Long designerId) {
        return processRepository.findAllByDesignerId(designerId).stream()
                .map(process -> BiddingMapper.toProcessDomain(
                        process,
                        threadRepository.findByBiddingProcessId(process.getId())
                ))
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
    public List<BiddingProcess> getProcessesByPuppyId(Long puppyId) {
        return processRepository.findByPuppyId(puppyId).stream()
                .map(processEntity -> {
                    List<BiddingThreadEntity> threadEntities = threadRepository.findByBiddingProcessId(processEntity.getId());
                    return BiddingMapper.toProcessDomain(processEntity, threadEntities);
                })
                .toList();
    }


    @Override
    public BiddingProcess getProcessByProcessIdAndPuppyId(Long processId, Long puppyId) {
        BiddingProcessEntity foundProcessEntity = processRepository.findByIdAndPuppyId(processId, puppyId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_PROCESS));
        List<BiddingThreadEntity> foundThreadEntities = threadRepository.findByBiddingProcessId(foundProcessEntity.getId());
        return BiddingMapper.toProcessDomain(foundProcessEntity, foundThreadEntities);
    }
}
