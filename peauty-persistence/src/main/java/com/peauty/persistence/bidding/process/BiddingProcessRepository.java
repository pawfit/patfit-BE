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

    // TODO 만약 한 강아지가 여러 프로세스를 가질 수 있으면 List 로 반환
    Optional<BiddingProcessEntity> findByPuppyId(@Param("puppyId") Long puppyId);
    Optional<BiddingProcessEntity> findByIdAndPuppyId(@Param("processId") Long processId, @Param("puppyId") Long puppyId);
    long countByPuppyIdAndStatusIn(@Param("puppyId") Long puppyId, List<BiddingProcessStatus> statuses);

    @Query("SELECT DISTINCT bp FROM BiddingProcessEntity bp " +
            "INNER JOIN BiddingThreadEntity bt ON bt.biddingProcess = bp " +
            "WHERE bt.designerId = :designerId")
    List<BiddingProcessEntity> findAllByDesignerId(@Param("designerId") Long designerId);
}