package com.peauty.customer.implementaion.bidding;

import com.peauty.domain.bidding.*;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;

import java.util.List;

public class BiddingMapper {

    public static BiddingProcess toProcessDomain(
            BiddingProcessEntity processEntity,
            List<BiddingThreadEntity> threadEntities
    ) {
        List<BiddingThread> threads = threadEntities.stream()
                .map(BiddingMapper::toThreadDomain)
                .toList();

        return BiddingProcess.loadProcess(
                new BiddingProcess.ID(processEntity.getId()),
                new PuppyId(processEntity.getPuppyId()),
                processEntity.getStatus(),
                new BiddingProcessTimeInfo(processEntity.getCreatedAt(), processEntity.getStatusModifiedAt()),
                threads
        );
    }

    public static BiddingProcessEntity toProcessEntity(BiddingProcess process) {
        return BiddingProcessEntity.builder()
                .id(process.getId().map(BiddingProcess.ID::value).orElse(null))
                .puppyId(process.getPuppyId().value())
                .status(process.getStatus())
                .createdAt(process.getTimeInfo().getCreatedAt())
                .statusModifiedAt(process.getTimeInfo().getStatusModifiedAt())
                .build();
    }

    public static BiddingThread toThreadDomain(BiddingThreadEntity threadEntity) {
        return BiddingThread.loadThread(
                new BiddingThread.ID(threadEntity.getId()),
                new BiddingProcess.ID(threadEntity.getBiddingProcessId()),
                new DesignerId(threadEntity.getDesignerId()),
                threadEntity.getStep(),
                threadEntity.getStatus(),
                new BiddingThreadTimeInfo(
                        threadEntity.getCreatedAt(),
                        threadEntity.getStepModifiedAt(),
                        threadEntity.getStatusModifiedAt()
                )
        );
    }

    public static BiddingThreadEntity toThreadEntity(BiddingThread thread) {
        return BiddingThreadEntity.builder()
                .id(thread.getId().map(BiddingThread.ID::value).orElse(null))
                .biddingProcessId(thread.getProcessId().value())
                .designerId(thread.getDesignerId().value())
                .step(thread.getStep())
                .status(thread.getStatus())
                .createdAt(thread.getTimeInfo().getCreatedAt())
                .stepModifiedAt(thread.getTimeInfo().getStepModifiedAt())
                .statusModifiedAt(thread.getTimeInfo().getStatusModifiedAt())
                .build();
    }

    public static List<BiddingThreadEntity> toThreadEntities(List<BiddingThread> threads) {
        return threads.stream()
                .map(BiddingMapper::toThreadEntity)
                .toList();
    }
}