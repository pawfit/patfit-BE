package com.peauty.customer.business.designer;

import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;

import java.util.List;

public interface DesignerPort {
    List<Badge> getRepresentativeBadges(Long userId);
    List<Badge> getAllBadges(); // 전체 뱃지
    List<Badge> getAcquiredBadges(Long userId);
    Designer getAllDesignerDataByDesignerId(Long userId);
    Designer.DesignerProfile getDesignerProfileByDesignerId(Long designerId);
    Designer getDesignerById(Long designerId);
    void checkExistsDesignersByDesignerIds(List<Long> designerIds);
}
