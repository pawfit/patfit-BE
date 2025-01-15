package com.peauty.customer.implementaion.workspace;

import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.*;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.ReviewRating;
import com.peauty.persistence.customer.CustomerMapper;
import com.peauty.persistence.designer.DesignerRepository;
import com.peauty.persistence.designer.badge.BadgeEntity;
import com.peauty.persistence.designer.badge.BadgeRepository;
import com.peauty.persistence.designer.badge.DesignerBadgeRepository;
import com.peauty.persistence.designer.mapper.WorkspaceMapper;
import com.peauty.persistence.designer.rating.RatingEntity;
import com.peauty.persistence.designer.rating.RatingRepository;
import com.peauty.persistence.designer.workspace.BannerImageEntity;
import com.peauty.persistence.designer.workspace.BannerImageRepository;
import com.peauty.persistence.designer.workspace.WorkspaceEntity;
import com.peauty.persistence.designer.workspace.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {

    private final DesignerRepository designerRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BadgeRepository badgeRepository;
    private final DesignerBadgeRepository designerBadgeRepository;
    private final RatingRepository ratingRepository;
    private final BannerImageRepository bannerImageRepository;

    @Override
    public List<Workspace> findAllWorkspaceByAddress(String baseAddress) {
        return workspaceRepository.findByBaseAddress(baseAddress)
                .stream()
                .map(workspaceEntity -> {
                    Rating rating = getRatingByWorkspaceId(workspaceEntity.getId());
                    List<BannerImageEntity> bannerImageEntities = bannerImageRepository.findByWorkspaceId(workspaceEntity.getId());
                    return CustomerMapper.toWorkspaceDomain(workspaceEntity, rating, bannerImageEntities); // Rating 포함
                })
                .toList();
    }

    /*  TODO: 뱃지 붙이기 전의 findDesignerById
        @Override
        public Designer findDesignerById(Long designerId) {
            return designerRepository.findById(designerId)
                    .map(CustomerMapper::toDesignerDomain)
                    .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
        }*/
@Override
public Designer findDesignerById(Long designerId) {
    return designerRepository.findById(designerId)
            .map(entity -> {
                Designer designer = CustomerMapper.toDesignerDomain(entity);

                // DesignerBadgeEntity에서 대표 뱃지를 조회
                List<Badge> badges = designerBadgeRepository.findAllByDesignerIdAndIsRepresentativeBadge(designerId, true)
                        .stream()
                        .map(designerBadge -> {
                            BadgeEntity badgeEntity = badgeRepository.findById(designerBadge.getBadgeId())
                                    .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
                            return CustomerMapper.toBadgeDomain(badgeEntity);
                        })
                        .toList();

                designer.updateBadges(badges); // 대표 뱃지 업데이트
                return designer;
            })
            .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
            }

    @Override
    public Rating getRatingByWorkspaceId(Long workspaceId) {
        return ratingRepository.findRatingByWorkspaceId(workspaceId)
                .map(CustomerMapper::toRatingDomain)
                .orElseGet(() -> Rating.builder()
                        .scissors(Scissors.NONE)
                        .build());
    }

    @Override
    public Workspace findByDesignerId(Long userId) {

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
    @Transactional
    public Workspace registerReviewStats(Long designerId, ReviewRating newRating
// TODO: InterruptedException을 WorkspacePort에서 Workspace registerReviewStats(Long designerId, ReviewRating newRating) throws InterruptedException; 이렇게 걸어주는 것이 맞을까?
// 아니면 내부 try-catch에서 InterruptedException을 거는게 맞을까?
    ) {
        while (true) {
            try {
                WorkspaceEntity workspaceEntity = workspaceRepository.findByDesignerIdWithOptimisticLock(designerId) // Workspace 엔티티에서 id를 호출해올 때, 버전을 확인
                        .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_WORKSPACE));
                List<BannerImageEntity> bannerImageEntities = bannerImageRepository.findByWorkspaceId(workspaceEntity.getId());
                Workspace workspace = WorkspaceMapper.toDomain(workspaceEntity, bannerImageEntities);
                // 리뷰 작성 로직
                workspace.registerReviewStats(newRating);
                // 엔티티 변환 후 저장
                workspaceEntity = WorkspaceMapper.toEntity(workspace, designerId);
                workspaceRepository.save(workspaceEntity);

                return workspace;
            } catch (ObjectOptimisticLockingFailureException e) {
                try {
                    Thread.sleep(10); // 10ms 대기 // 저장 작업을 수행하는 하나의 실행 단위(Thread)
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태 복구
                    throw new PeautyException(PeautyResponseCode.INTERNAL_SERVER_MAINTENANCE);
                }
            }
        }
    }

    public Workspace updateReviewStats(Long designerId, ReviewRating oldRating, ReviewRating newRating) {
        WorkspaceEntity workspaceEntity = workspaceRepository.getByDesignerId(designerId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_WORKSPACE));
        List<BannerImageEntity> bannerImageEntities = bannerImageRepository.findByWorkspaceId(workspaceEntity.getId());

        Workspace workspace = WorkspaceMapper.toDomain(workspaceEntity, bannerImageEntities);

        // 리뷰 수정 로직
        workspace.updateReviewStats(oldRating, newRating);
        // 엔티티 변환 후 저장
        workspaceEntity = WorkspaceMapper.toEntity(workspace, designerId);
        workspaceRepository.save(workspaceEntity);

        return workspace;
    }

    public Workspace deleteReviewStats(Long designerId, ReviewRating deletedRating) {
        WorkspaceEntity workspaceEntity = workspaceRepository.getByDesignerId(designerId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_WORKSPACE));
        List<BannerImageEntity> bannerImageEntities = bannerImageRepository.findByWorkspaceId(workspaceEntity.getId());

        Workspace workspace = WorkspaceMapper.toDomain(workspaceEntity, bannerImageEntities);

        // 리뷰 삭제 로직
        workspace.deleteReviewStats(deletedRating);

        // 엔티티 변환 후 저장
        workspaceEntity = WorkspaceMapper.toEntity(workspace, designerId);
        workspaceRepository.save(workspaceEntity);

        return workspace;
    }


}
