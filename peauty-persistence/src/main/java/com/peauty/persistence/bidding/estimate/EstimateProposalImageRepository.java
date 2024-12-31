package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateProposalImageRepository extends JpaRepository<EstimateProposalImageEntity, Long> {

    List<EstimateProposalImageEntity> findByEstimateProposalId(Long estimateProposalId);
}
