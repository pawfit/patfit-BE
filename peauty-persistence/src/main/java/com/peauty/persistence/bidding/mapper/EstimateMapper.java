package com.peauty.persistence.bidding.mapper;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateImage;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import com.peauty.persistence.bidding.estimate.EstimateImageEntity;

import java.util.List;

public class EstimateMapper {

    public static EstimateEntity toEstimateEntity(Estimate domain) {
        return EstimateEntity.builder()
                .id(domain.getId()
                        .map(Estimate.ID::value)
                        .orElse(null))
                .biddingThreadId(domain.getThreadId().value())
                .content(domain.getContent())
                .availableGroomingDate(domain.getAvailableGroomingDate())
                .estimatedDuration(domain.getEstimatedDuration())
                .estimatedCost(domain.getEstimatedCost())
                .build();
    }

    public static EstimateImageEntity toImageEntity(EstimateImage domain, EstimateEntity estimateEntity) {
        return EstimateImageEntity.builder()
                .id(domain.getId()
                        .map(EstimateImage.ID::value)
                        .orElse(null)
                )
                .estimate(estimateEntity)
                .imageUrl(domain.getImageUrl())
                .build();
    }

    public static Estimate toEstimateDomain(EstimateEntity entity, List<EstimateImageEntity> imageEntities) {
        List<EstimateImage> images = imageEntities.stream()
                .map(EstimateMapper::toImageDomain)
                .toList();

        return Estimate.builder()
                .id(new Estimate.ID(entity.getId()))
                .threadId(new BiddingThread.ID(entity.getBiddingThreadId()))
                .content(entity.getContent())
                .availableGroomingDate(entity.getAvailableGroomingDate())
                .estimatedDuration(entity.getEstimatedDuration())
                .estimatedCost(entity.getEstimatedCost())
                .images(images)
                .build();
    }

    public static EstimateImage toImageDomain(EstimateImageEntity entity) {
        return EstimateImage.builder()
                .id(new EstimateImage.ID(entity.getId()))
                .estimateId(new Estimate.ID(entity.getEstimate().getId()))
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
