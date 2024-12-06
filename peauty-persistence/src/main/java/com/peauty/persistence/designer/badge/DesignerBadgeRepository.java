package com.peauty.persistence.designer.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignerBadgeRepository extends JpaRepository<DesignerBadgeEntity, Long> {
    @Query("SELECT db.badgeId FROM DesignerBadgeEntity db WHERE db.designerId = :designerId AND db.isRepresentativeBadge = true")
    List<Long> findRepresentativeBadgeIdsByDesignerId(@Param("designerId") Long designerId);

    List<DesignerBadgeEntity> findAllByDesignerIdAndIsRepresentativeBadge(Long designerId, boolean isRepresentativeBadge);

    List<DesignerBadgeEntity> findAllByDesignerId(Long designerId);

}
