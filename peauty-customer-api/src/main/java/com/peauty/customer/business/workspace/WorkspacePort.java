package com.peauty.customer.business.workspace;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;

import java.util.List;

public interface WorkspacePort {

    List<Workspace> findAllWorkspaceByAddress(String address);
    Designer findDesignerById(Long designerId);

}
