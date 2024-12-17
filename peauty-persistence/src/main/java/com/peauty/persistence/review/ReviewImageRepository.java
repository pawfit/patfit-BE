package com.peauty.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {

    List<ReviewImageEntity> findAllByReviewId(Long reviewId);

    // 특정 리뷰 ID에 해당하는 이미지가 존재하는지 확인
    boolean existsByReviewId(Long reviewId);

    // 특정 리뷰 ID에 해당하는 이미지들 삭제
    void deleteAllByReviewId(Long reviewId);

}
