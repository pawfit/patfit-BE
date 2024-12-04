package com.peauty.persistence.bidding.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BiddingProcessRepository extends JpaRepository<BiddingProcessEntity, Long> {

    List<BiddingProcessEntity> findByPuppyId(Long puppyId);

    @Query("""
            SELECT p\s
            FROM BiddingProcessEntity p, BiddingThreadEntity t\s
            WHERE t.biddingProcessId = p.id\s
            AND t.id = :threadId
            """)
    Optional<BiddingProcessEntity> findByThreadId(@Param("threadId") Long threadId);

    @Query("""
            SELECT DISTINCT p\s
            FROM BiddingProcessEntity p, BiddingThreadEntity t\s
            WHERE t.biddingProcessId = p.id\s
            AND t.designerId = :designerId
            """)
    List<BiddingProcessEntity> findAllByDesignerId(@Param("designerId") Long designerId);
}