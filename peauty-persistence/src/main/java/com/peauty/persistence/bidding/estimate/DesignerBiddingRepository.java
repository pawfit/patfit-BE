package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerBiddingRepository extends JpaRepository<EstimateEntity, Long> {
}
