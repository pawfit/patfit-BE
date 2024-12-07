package com.peauty.customer.business.workspace;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;

import java.util.List;

public interface WorkspacePort {

    List<Workspace> findAllWorkspaceByAddress(String address);
    Designer findDesignerById(Long designerId);
    Rating getRatingByWorkspaceId(Long workspaceId); // 추가된 메서드
    Workspace getByDesignerId(Long designerId);
}
