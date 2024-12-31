package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstimateRepository extends JpaRepository<EstimateEntity, Long> {

    Optional<EstimateEntity> findByBiddingThreadId(Long threadId);
}
