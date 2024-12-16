package com.peauty.persistence.bidding.process;

import com.peauty.domain.bidding.BiddingProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BiddingProcessRepository extends JpaRepository<BiddingProcessEntity, Long> {

    List<BiddingProcessEntity> findByPuppyId(@Param("puppyId") Long puppyId);
    Optional<BiddingProcessEntity> findByIdAndPuppyId(@Param("processId") Long processId, @Param("puppyId") Long puppyId);
    long countByPuppyIdAndStatusIn(@Param("puppyId") Long puppyId, List<BiddingProcessStatus> statuses);

    @Query("SELECT bp FROM BiddingProcessEntity bp " +
            "INNER JOIN BiddingThreadEntity bt ON bt.biddingProcess = bp " +
            "WHERE bt.designerId = :designerId")
    List<BiddingProcessEntity> findAllByDesignerId(@Param("designerId") Long designerId);

    @Query("SELECT bp FROM BiddingProcessEntity bp JOIN PuppyEntity p ON bp.puppyId = p.id WHERE p.customer.id = :userId")
    List<BiddingProcessEntity> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT bp FROM BiddingProcessEntity bp " +
            "WHERE bp.puppyId = :puppyId " +
            "AND bp.status IN (com.peauty.domain.bidding.BiddingProcessStatus.RESERVED, " +
            "com.peauty.domain.bidding.BiddingProcessStatus.RESERVED_YET)")
    Optional<BiddingProcessEntity> findOngoingProcessByPuppyId(@Param("puppyId") Long puppyId);

    Boolean existsByPuppyIdAndStatusIn(@Param("puppyId") Long puppyId, List<BiddingProcessStatus> statuses);
}