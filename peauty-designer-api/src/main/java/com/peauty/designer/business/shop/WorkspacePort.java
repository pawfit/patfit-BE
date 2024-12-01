package com.peauty.designer.business.shop;

import com.peauty.domain.designer.Workspace;

public interface WorkspacePort {
    Workspace save(Workspace workspace, Long designerId);

    Workspace getByDesignerId(Long userId);
}
