package com.peauty.customer.implementaion.bidding;

import com.peauty.domain.bidding.*;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;

import java.util.List;

public class BiddingMapper {

    public static BiddingProcess toProcessDomain(BiddingProcessEntity entity) {
        List<BiddingThread> threads = entity.getThreads().stream()
                .map(BiddingMapper::toThreadDomain)
                .toList();

        return BiddingProcess.loadProcess(
                new BiddingProcess.ID(entity.getId()),
                new PuppyId(entity.getPuppyId()),
                entity.getStatus(),
                new BiddingProcessTimeInfo(
                        entity.getCreatedAt(),
                        entity.getStatusModifiedAt()
                ),
                threads
        );
    }

    public static BiddingProcessEntity toProcessEntity(BiddingProcess domain) {
        return BiddingProcessEntity.builder()
                .id(domain.getId().map(BiddingProcess.ID::value).orElse(null))
                .puppyId(domain.getPuppyId().value())
                .status(domain.getStatus())
                .createdAt(domain.getTimeInfo().getCreatedAt())
                .statusModifiedAt(domain.getTimeInfo().getStatusModifiedAt())
                .build();
    }

    public static BiddingThread toThreadDomain(BiddingThreadEntity entity) {
        return BiddingThread.loadThread(
                new BiddingThread.ID(entity.getId()),
                new BiddingProcess.ID(entity.getBiddingProcess().getId()),
                new DesignerId(entity.getDesignerId()),
                entity.getStep(),
                entity.getStatus(),
                new BiddingThreadTimeInfo(
                        entity.getCreatedAt(),
                        entity.getStepModifiedAt(),
                        entity.getStatusModifiedAt()
                )
        );
    }

    public static BiddingThreadEntity toThreadEntity(BiddingThread domain, BiddingProcessEntity processEntity) {
        return BiddingThreadEntity.builder()
                .id(domain.getId().map(BiddingThread.ID::value).orElse(null))
                .biddingProcess(processEntity)
                .designerId(domain.getDesignerId().value())
                .step(domain.getStep())
                .status(domain.getStatus())
                .createdAt(domain.getTimeInfo().getCreatedAt())
                .stepModifiedAt(domain.getTimeInfo().getStepModifiedAt())
                .statusModifiedAt(domain.getTimeInfo().getStatusModifiedAt())
                .build();
    }
}