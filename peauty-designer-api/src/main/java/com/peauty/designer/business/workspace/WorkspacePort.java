package com.peauty.designer.business.workspace;

import com.peauty.domain.designer.Workspace;

public interface WorkspacePort {
    Workspace registerNew(Workspace workspace, Long designerId);

    Workspace findByDesignerId(Long userId);
    Workspace updateDesginerWorkspace(Long userId, Workspace workspace);
}
