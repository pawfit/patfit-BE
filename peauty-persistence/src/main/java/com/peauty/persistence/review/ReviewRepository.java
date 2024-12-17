package com.peauty.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByIdAndBiddingThreadId(Long id, Long biddingThreadId);
    Boolean existsByBiddingThreadId(Long biddingThreadId);
    Optional<ReviewEntity> findByBiddingThreadId(Long biddingThreadId);

    @Query("""
        SELECT r FROM ReviewEntity r
        JOIN BiddingThreadEntity bt ON r.biddingThreadId = bt.id
        JOIN BiddingProcessEntity bp ON bt.biddingProcess.id = bp.id
        JOIN PuppyEntity p ON bp.puppyId = p.id
        JOIN CustomerEntity c ON p.customer.id = c.id
        WHERE c.id = :customerId
    """)
    List<ReviewEntity> findAllByCustomerId(@Param("customerId") Long customerId);



}
