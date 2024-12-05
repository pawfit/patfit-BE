package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateProposalRepository extends JpaRepository<EstimateProposalEntity, Long> {

}
