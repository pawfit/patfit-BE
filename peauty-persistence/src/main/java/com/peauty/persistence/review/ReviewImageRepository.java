package com.peauty.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {

    List<ReviewImageEntity> findAllByReviewId(Long reviewId);

    void deleteAllImagesByReviewId(Long reviewId);
}
