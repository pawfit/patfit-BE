package com.peauty.customer.business.workspace;

import com.peauty.customer.business.workspace.dto.GetDesignerWorkspaceResult;

public interface WorkspaceService {
    GetDesignerWorkspaceResult getWorkspaceDetail(Long workspaceId);
}
