package com.peauty.persistence.bidding.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingProcessRepository extends JpaRepository<BiddingProcessEntity, Long> {

    List<BiddingProcessEntity> findByPuppyId(@Param("puppyId") Long puppyId);
}