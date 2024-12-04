package com.peauty.customer.implementaion.bidding;

import com.peauty.customer.business.bidding.EstimateProposalPort;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.persistence.bidding.estimate.EstimateProposalEntity;
import com.peauty.persistence.bidding.estimate.EstimateProposalImageEntity;
import com.peauty.persistence.bidding.estimate.EstimateProposalImageRepository;
import com.peauty.persistence.bidding.estimate.EstimateProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstimateProposalAdapter implements EstimateProposalPort {

    private final EstimateProposalRepository estimateProposalRepository;
    private final EstimateProposalImageRepository estimateProposalImageRepository;

    @Override
    public EstimateProposal save(EstimateProposal proposal) {
        EstimateProposalEntity savedProposalEntity = estimateProposalRepository.save(
                EstimateProposalMapper.toEntity(proposal)
        );

        List<EstimateProposalImageEntity> imageEntities = proposal.getImages().stream()
                .map(image -> EstimateProposalMapper.toImageEntity(image, savedProposalEntity))
                .toList();

        List<EstimateProposalImageEntity> savedImageEntities = estimateProposalImageRepository.saveAll(imageEntities);

        return EstimateProposalMapper.toDomain(savedProposalEntity, savedImageEntities);
    }
}
