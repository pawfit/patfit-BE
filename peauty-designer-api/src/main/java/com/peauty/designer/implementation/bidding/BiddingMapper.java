package com.peauty.designer.implementation.bidding;

import com.peauty.domain.bidding.*;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;

import java.util.List;

public class BiddingMapper {

    public static BiddingProcess toProcessDomain(BiddingProcessEntity processEntity, List<BiddingThreadEntity> threadEntities) {
        List<BiddingThread> threads = threadEntities.stream()
                .map(BiddingMapper::toThreadDomain)
                .toList();

        return BiddingProcess.loadProcess(
                new BiddingProcess.ID(processEntity.getId()),
                new PuppyId(processEntity.getPuppyId()),
                processEntity.getStatus(),
                new BiddingProcessTimeInfo(
                        processEntity.getCreatedAt(),
                        processEntity.getStatusModifiedAt()
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

    public static EstimateEntity toEstimateEntity(Estimate domain) {
        return EstimateEntity.builder()
                .id(domain.getId().map(Estimate.ID::value).orElse(null))
                .biddingThreadId(domain.getThreadId().value())
                .content(domain.getContent())
                .date(domain.getDate())
                .cost(domain.getCost())
                .proposalImageUrl(domain.getProposalImageUrl())
                .build();
    }


    public static Estimate toEstimateDomain(EstimateEntity savedEstimateEntity) {
        return null;
    }
}