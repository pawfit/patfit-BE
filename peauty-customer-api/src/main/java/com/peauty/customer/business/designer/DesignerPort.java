package com.peauty.customer.business.designer;

import com.peauty.domain.designer.Badge;

import java.util.List;

public interface DesignerPort {

    List<Badge> getBadges(Long userId);
}
