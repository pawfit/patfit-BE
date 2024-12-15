package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstimateProposalRepository extends JpaRepository<EstimateProposalEntity, Long> {

    Optional<EstimateProposalEntity> findByProcessId(Long processId);

    List<EstimateProposalEntity> findByProcessIdIn(Collection<Long> processIds);
}
