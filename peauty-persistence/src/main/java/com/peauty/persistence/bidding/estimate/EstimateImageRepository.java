package com.peauty.persistence.bidding.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateImageRepository extends JpaRepository<EstimateImageEntity, Long> {

    List<EstimateImageEntity> findByEstimateId(Long estimateId);
}
