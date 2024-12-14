package com.peauty.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByIdAndBiddingThreadId(Long id, Long biddingThreadId);
    Boolean existsByBiddingThreadId(Long biddingThreadId);
}
