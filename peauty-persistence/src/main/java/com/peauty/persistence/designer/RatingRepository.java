package com.peauty.persistence.designer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    RatingEntity findByWorkspaceId(Long id);
}
