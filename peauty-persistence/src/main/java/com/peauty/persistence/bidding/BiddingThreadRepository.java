package com.peauty.persistence.bidding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingThreadRepository extends JpaRepository<BiddingThreadEntity, Long> {

    List<BiddingThreadEntity> findByBiddingProcessId(Long biddingProcessId);
}
