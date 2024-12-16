package com.peauty.designer.implementation.workspace;

import com.peauty.designer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {
    private final WorkspaceRepository workspaceRepository;
    private final RatingRepository ratingRepository;
    private final BannerImageRepository bannerImageRepository;

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
    public Workspace getByDesignerId(Long userId) {

        WorkspaceEntity workspaceEntity = workspaceRepository.getByDesignerId(userId)
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
        // 1. Workspace 업데이트
        WorkspaceEntity workspaceEntityToUpdate = WorkspaceMapper.toEntity(workspace, userId);
        WorkspaceEntity updatedWorkspaceEntity = workspaceRepository.save(workspaceEntityToUpdate);

        // 2. Rating 조회
        RatingEntity ratingEntity = ratingRepository.findByWorkspaceId(workspaceEntityToUpdate.getId()).orElse(null);

        // TODO. 나만 쓰기 아깝다.. 메서드로 뺴서 팀원 모두가 사용할 수 있게 해보자!
        // 3. 기존 배너 이미지 엔티티 가져오기
        List<BannerImageEntity> existingEntities = bannerImageRepository.findByWorkspaceId(workspaceEntityToUpdate.getId());

        // 4. 새로운 URL 리스트 가져오기
        List<String> updatedUrls = workspace.getBannerImageUrls();

        // 기존 URL 목록과 새로운 URL 목록을 비교
        Set<String> existingUrls = existingEntities.stream()
                .map(BannerImageEntity::getBannerImageUrl)
                .collect(Collectors.toSet());
        Set<String> newUrls = new HashSet<>(updatedUrls);

        // 삭제할 엔티티: 기존 엔티티 중 새로운 URL에 없는 것
        List<BannerImageEntity> entitiesToDelete = existingEntities.stream()
                .filter(entity -> !newUrls.contains(entity.getBannerImageUrl()))
                .collect(Collectors.toList());

        // 추가할 엔티티: 새로운 URL 중 기존에 없는 것
        List<BannerImageEntity> entitiesToUpdate = updatedUrls.stream()
                .filter(url -> !existingUrls.contains(url))
                .map(url -> BannerImageEntity.builder()
                        .workspaceId(workspaceEntityToUpdate.getId())
                        .bannerImageUrl(url)
                        .build())
                .collect(Collectors.toList());

        bannerImageRepository.deleteAll(entitiesToDelete);
        bannerImageRepository.saveAll(entitiesToUpdate);

        // 6. 최종 배너 이미지 리스트 조회
        List<BannerImageEntity> finalBannerImageEntities =
                bannerImageRepository.findByWorkspaceId(workspaceEntityToUpdate.getId());

        // 7. Rating 변환 및 Workspace 도메인 변환
        Rating rating = WorkspaceMapper.toRatingDomain(ratingEntity);
        Workspace updatedWorkspace = WorkspaceMapper.toDomain(updatedWorkspaceEntity, finalBannerImageEntities);
        updatedWorkspace.updateRating(rating);

        return updatedWorkspace;
    }
}
