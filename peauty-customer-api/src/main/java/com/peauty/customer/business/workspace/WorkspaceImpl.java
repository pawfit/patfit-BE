package com.peauty.customer.business.workspace;

import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.workspace.dto.GetDesignerWorkspaceResult;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceImpl implements WorkspaceService {
    private final DesignerPort designerPort;
    private final WorkspacePort workspacePort;

    @Override
    public GetDesignerWorkspaceResult getWorkspaceDetail(Long designerId) {
        Designer designer = designerPort.getAllDesignerDataByDesignerId(designerId);
        Workspace workspace = workspacePort.getByDesignerId(designerId);

        designer.updateBadges(designerPort.getRepresentativeBadges(designerId));
        return GetDesignerWorkspaceResult.from(designer, workspace);
    }
}
