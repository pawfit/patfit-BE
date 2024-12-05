package com.peauty.customer.implementaion.bidding;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.bidding.EstimateProposalImage;
import com.peauty.persistence.bidding.estimate.EstimateProposalEntity;
import com.peauty.persistence.bidding.estimate.EstimateProposalImageEntity;

import java.util.List;

public class EstimateProposalMapper {

    public static EstimateProposalEntity toProposalEntity(EstimateProposal domain) {
        return EstimateProposalEntity.builder()
                .biddingProcessId(domain.getProcessId().value())
                .type(domain.getType())
                .detail(domain.getDetail())
                .desiredCost(domain.getDesiredCost())
                .desiredDateTime(domain.getDesiredDateTime())
                .totalGroomingBodyType(domain.getTotalGroomingBodyType())
                .totalGroomingFaceType(domain.getTotalGroomingFaceType())
                .build();
    }

    public static EstimateProposalImageEntity toImageEntity(EstimateProposalImage domain, EstimateProposalEntity proposalEntity) {
        return EstimateProposalImageEntity.builder()
                .estimateProposal(proposalEntity)
                .imageUrl(domain.getImageUrl())
                .build();
    }

    public static EstimateProposal toProposalDomain(EstimateProposalEntity entity, List<EstimateProposalImageEntity> images) {
        List<EstimateProposalImage> proposalImages = images.stream()
                .map(EstimateProposalMapper::toImageDomain)
                .toList();

        return EstimateProposal.builder()
                .id(new EstimateProposal.ID(entity.getId()))
                .processId(new BiddingProcess.ID(entity.getBiddingProcessId()))
                .type(entity.getType())
                .detail(entity.getDetail())
                .desiredCost(entity.getDesiredCost())
                .desiredDateTime(entity.getDesiredDateTime())
                .totalGroomingBodyType(entity.getTotalGroomingBodyType())
                .totalGroomingFaceType(entity.getTotalGroomingFaceType())
                .images(proposalImages)
                .build();
    }

    public static EstimateProposalImage toImageDomain(EstimateProposalImageEntity entity) {
        return EstimateProposalImage.builder()
                .id(new EstimateProposalImage.ID(entity.getId()))
                .estimateProposalId(new EstimateProposal.ID(entity.getEstimateProposal().getId()))
                .imageUrl(entity.getImageUrl())
                .build();
    }
}