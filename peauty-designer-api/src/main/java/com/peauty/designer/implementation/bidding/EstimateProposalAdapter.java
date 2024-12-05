package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.EstimateProposalPort;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
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
                EstimateProposalMapper.toProposalEntity(proposal)
        );
        List<EstimateProposalImageEntity> imageEntities = proposal.getImages().stream()
                .map(image -> EstimateProposalMapper.toImageEntity(image, savedProposalEntity))
                .toList();
        List<EstimateProposalImageEntity> savedImageEntities = estimateProposalImageRepository.saveAll(imageEntities);
        return EstimateProposalMapper.toProposalDomain(savedProposalEntity, savedImageEntities);
    }

    @Override
    public EstimateProposal getProposalById(Long proposalId) {
        EstimateProposalEntity foundProposalEntity = estimateProposalRepository.findById(proposalId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_ESTIMATE_PROPOSAL));
        List<EstimateProposalImageEntity> foundProposalImageEntities = estimateProposalImageRepository.findByEstimateProposalId(foundProposalEntity.getId());
        return EstimateProposalMapper.toProposalDomain(foundProposalEntity, foundProposalImageEntities);
    }
}