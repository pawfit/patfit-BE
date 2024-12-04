package com.peauty.persistence.bidding.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BiddingProcessRepository extends JpaRepository<BiddingProcessEntity, Long> {

    @Query("SELECT p FROM BiddingProcessEntity p " +
            "LEFT JOIN FETCH p.threads " +
            "WHERE p.puppyId = :puppyId")
    List<BiddingProcessEntity> findByPuppyIdWithThread(@Param("puppyId") Long puppyId);

    @Query("SELECT p FROM BiddingProcessEntity p " +
            "LEFT JOIN FETCH p.threads " +
            "WHERE p.id = :id")
    Optional<BiddingProcessEntity> findByIdWithThread(@Param("id") Long id);
}