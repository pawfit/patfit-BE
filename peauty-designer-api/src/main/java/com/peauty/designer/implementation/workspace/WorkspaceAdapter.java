package com.peauty.designer.implementation.workspace;

import com.peauty.designer.business.shop.WorkspacePort;
import com.peauty.domain.designer.Workspace;
import com.peauty.persistence.designer.WorkspaceEntity;
import com.peauty.persistence.designer.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {
    private final WorkspaceRepository workspaceRepository;

    @Override
    public Workspace save(Workspace workspace, Long designerId) {
        WorkspaceEntity workspaceEntityToSave = WorkspaceMapper.toEntity(workspace, designerId);
        WorkspaceEntity savedWorkspaceEntity = workspaceRepository.save(workspaceEntityToSave);
        return WorkspaceMapper.toDomain(savedWorkspaceEntity);
    }
}
