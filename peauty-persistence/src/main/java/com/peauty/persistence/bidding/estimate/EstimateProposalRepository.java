package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateProposalRepository extends JpaRepository<EstimateProposalEntity, Long> {

    @Query("SELECT p FROM EstimateProposalEntity p " +
            "LEFT JOIN FETCH p.images " +
            "WHERE p.id = :id")
    Optional<EstimateProposalEntity> findByIdWithImages(@Param("id") Long id);
}
