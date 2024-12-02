package com.peauty.designer.business.workspace;

import com.peauty.domain.designer.Workspace;

public interface WorkspacePort {
    Workspace save(Workspace workspace, Long designerId);

    Workspace getByDesignerId(Long userId);
    Workspace updateDesginerWorkspace(Long userId, Workspace workspace);
}
