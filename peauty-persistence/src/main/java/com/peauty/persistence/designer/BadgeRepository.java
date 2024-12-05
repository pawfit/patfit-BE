package com.peauty.persistence.designer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<BadgeEntity, Long> {

    @Query("SELECT b FROM BadgeEntity b WHERE b.id = :badgeId")
    Optional<BadgeEntity> findBadgeById(@Param("badgeId") Long badgeId);

    Optional<BadgeEntity> findById(Long id);
}
