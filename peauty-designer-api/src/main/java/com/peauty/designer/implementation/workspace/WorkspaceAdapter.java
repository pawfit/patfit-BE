package com.peauty.designer.implementation.workspace;

import com.peauty.designer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.designer.license.LicenseRepository;
import com.peauty.persistence.designer.mapper.WorkspaceMapper;
import com.peauty.persistence.designer.rating.RatingEntity;
import com.peauty.persistence.designer.rating.RatingRepository;
import com.peauty.persistence.designer.workspace.BannerImageEntity;
import com.peauty.persistence.designer.workspace.BannerImageRepository;
import com.peauty.persistence.designer.workspace.WorkspaceEntity;
import com.peauty.persistence.designer.workspace.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {
    private final WorkspaceRepository workspaceRepository;
    private final RatingRepository ratingRepository;
    private final BannerImageRepository bannerImageRepository;
    private final LicenseRepository licenseRepository;

    @Override
    public Workspace registerNew(Workspace workspace, Long designerId) {
        WorkspaceEntity workspaceEntityToSave = WorkspaceMapper.toEntity(workspace, designerId);
        if (workspaceRepository.existsByDesignerId(workspaceEntityToSave.getDesignerId())) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_WORKSPACE);
        }

        WorkspaceEntity savedWorkspaceEntity = workspaceRepository.save(workspaceEntityToSave);
        List<BannerImageEntity> bannerImageToSave = WorkspaceMapper.toBannerImageEntity(
                savedWorkspaceEntity.getId(), workspace.getBannerImageUrls());
        List<BannerImageEntity> savedBannerImageEntity = bannerImageRepository.saveAll(bannerImageToSave);
        return WorkspaceMapper.toDomain(savedWorkspaceEntity, savedBannerImageEntity);
    }

    @Override
    public Workspace findByDesignerId(Long userId) {

        WorkspaceEntity workspaceEntity = workspaceRepository.findByDesignerId(userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_WORKSPACE));
        RatingEntity ratingEntity = ratingRepository.findByWorkspaceId(workspaceEntity.getId())
                .orElse(null);
        List<BannerImageEntity> bannerImageEntities = bannerImageRepository.findByWorkspaceId(workspaceEntity.getId());

        Rating rating = WorkspaceMapper.toRatingDomain(ratingEntity);
        Workspace workspace = WorkspaceMapper.toDomain(workspaceEntity, bannerImageEntities);
        workspace.updateRating(rating);

        return workspace;
    }

    @Override
    public Workspace updateDesginerWorkspace(Long userId, Workspace workspace) {
        WorkspaceEntity workspaceEntityToUpdate = WorkspaceMapper.toEntity(workspace, userId);
        WorkspaceEntity updatedWorkspaceEntity = workspaceRepository.save(workspaceEntityToUpdate);
        RatingEntity ratingEntity = ratingRepository.findByWorkspaceId(workspaceEntityToUpdate.getId())
                .orElse(null);
        List<BannerImageEntity> bannerImageEntitiesToUpdate = bannerImageRepository.findByWorkspaceId(workspaceEntityToUpdate.getId());
        List<String> updatedUrls = workspace.getBannerImageUrls(); // 새로운 URL 리스트

        List<BannerImageEntity> updatedBannerImageEntities = IntStream.range(0, Math.min(bannerImageEntitiesToUpdate.size(), updatedUrls.size()))
                .mapToObj(i -> {
                    BannerImageEntity entity = bannerImageEntitiesToUpdate.get(i);
                    entity.updateBannerImageUrl(updatedUrls.get(i)); // URL만 업데이트
                    return entity;
                })
                .toList();

        List<BannerImageEntity> savedBannerImageEntities = bannerImageRepository.saveAll(updatedBannerImageEntities);

        Rating rating = WorkspaceMapper.toRatingDomain(ratingEntity);
        Workspace updatedWorkspace = WorkspaceMapper.toDomain(updatedWorkspaceEntity, savedBannerImageEntities);
        updatedWorkspace.updateRating(rating);
        return updatedWorkspace;
    }
}
