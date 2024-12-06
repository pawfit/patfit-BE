package com.peauty.persistence.designer.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

        Optional<RatingEntity> findRatingByWorkspaceId(Long workspaceId);
        RatingEntity findByWorkspaceId(Long id);

}
