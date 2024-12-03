package com.peauty.designer.implementation.workspace;

import com.peauty.designer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.designer.RatingEntity;
import com.peauty.persistence.designer.RatingRepository;
import com.peauty.persistence.designer.WorkspaceEntity;
import com.peauty.persistence.designer.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {
    private final WorkspaceRepository workspaceRepository;
    private final RatingRepository ratingRepository;

    @Override
    public Workspace save(Workspace workspace, Long designerId) {
        WorkspaceEntity workspaceEntityToSave = WorkspaceMapper.toEntity(workspace, designerId);
        WorkspaceEntity savedWorkspaceEntity = workspaceRepository.save(workspaceEntityToSave);
        return WorkspaceMapper.toDomain(savedWorkspaceEntity);
    }

    @Override
    public Workspace getByDesignerId(Long userId) {
        WorkspaceEntity workspaceEntity = Optional.ofNullable(workspaceRepository.findByDesignerId(userId))
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
        RatingEntity ratingEntity = Optional.ofNullable(ratingRepository.findByWorkspaceId(workspaceEntity.getId()))
                .orElse(null);

        Rating rating = WorkspaceMapper.toRatingDomain(ratingEntity);
        Workspace workspace = WorkspaceMapper.toDomain(workspaceEntity);
        workspace.updateRating(rating);
        return workspace;
    }

    @Override
    public Workspace updateDesginerWorkspace(Long userId, Workspace workspace) {
        WorkspaceEntity workspaceEntityToUpdate = WorkspaceMapper.toEntity(workspace, userId);
        WorkspaceEntity updatedWorkspaceEntity = workspaceRepository.save(workspaceEntityToUpdate);

        RatingEntity ratingEntity = Optional.ofNullable(ratingRepository.findByWorkspaceId(workspaceEntityToUpdate.getId()))
                .orElse(null);

        Rating rating = WorkspaceMapper.toRatingDomain(ratingEntity);
        Workspace updatedWorkspace = WorkspaceMapper.toDomain(updatedWorkspaceEntity);
        updatedWorkspace.updateRating(rating);
        return updatedWorkspace;
    }
}
