package com.peauty.customer.business.workspace;

import com.peauty.customer.business.workspace.dto.GetAroundWorkspacesResult;
import com.peauty.customer.business.workspace.dto.GetDesignerWorkspaceResult;

public interface WorkspaceService {
    GetDesignerWorkspaceResult getWorkspaceDetails(Long designerId);

    GetAroundWorkspacesResult getAroundWorkspaces(Long userId);
}
