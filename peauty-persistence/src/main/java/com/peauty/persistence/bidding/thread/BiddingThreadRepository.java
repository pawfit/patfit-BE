package com.peauty.persistence.bidding.thread;

import com.peauty.domain.bidding.BiddingThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingThreadRepository extends JpaRepository<BiddingThreadEntity, Long> {

    @Query("SELECT DISTINCT bt FROM BiddingThreadEntity bt " +
            "JOIN FETCH bt.biddingProcess bp " +
            "WHERE bt.designerId = :designerId")
    List<BiddingThreadEntity> findAllByDesignerIdWithBiddingProcess(@Param("designerId") Long designerId);
    List<BiddingThreadEntity> findAllByDesignerId(@Param("designerId") Long designerId);
    List<BiddingThreadEntity> findByBiddingProcessId(Long biddingProcessId);
}
