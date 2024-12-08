package com.peauty.customer.implementaion.designer;

import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.*;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.designer.DesignerRepository;
import com.peauty.persistence.designer.badge.BadgeEntity;
import com.peauty.persistence.designer.badge.BadgeRepository;
import com.peauty.persistence.designer.badge.DesignerBadgeEntity;
import com.peauty.persistence.designer.badge.DesignerBadgeRepository;
import com.peauty.persistence.designer.license.LicenseRepository;
import com.peauty.persistence.designer.mapper.DesignerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DesignerAdapter implements DesignerPort {

    private final BadgeRepository badgeRepository;
    private final DesignerBadgeRepository designerBadgeRepository;
    private final DesignerRepository designerRepository;
    private final LicenseRepository licenseRepository;
    private final WorkspacePort workspacePort;


    @Override
    public List<Badge> getRepresentativeBadges(Long userId) {
        List<Long> badgeIds = designerBadgeRepository.findRepresentativeBadgeIdsByDesignerId(userId);
        List<BadgeEntity> badgeEntities = badgeRepository.findAllById(badgeIds);
        return badgeEntities.stream()
                .map(badgeEntity -> Badge.builder()
                        .badgeId(badgeEntity.getId())
                        .badgeName(badgeEntity.getBadgeName())
                        .badgeContent(badgeEntity.getBadgeContent())
                        .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                        .isRepresentativeBadge(true)
                        .badgeColor(badgeEntity.getBadgeColor())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(badgeEntity -> Badge.builder()
                        .badgeId(badgeEntity.getId())
                        .badgeName(badgeEntity.getBadgeName())
                        .badgeContent(badgeEntity.getBadgeContent())
                        .badgeImageUrl(badgeEntity.getBadgeImageUrl())
//                        .badgeColor(badgeEntity.getBadgeColor())
                        .build())
                .toList();
    }

    @Override
    public List<Badge> getAcquiredBadges(Long userId) {
        List<DesignerBadgeEntity> designerBadges = designerBadgeRepository.findAllByDesignerId(userId);
        List<Long> badgeIds = designerBadges.stream()
                .map(DesignerBadgeEntity::getBadgeId)
                .toList();

        List<BadgeEntity> badgeEntities = badgeRepository.findAllById(badgeIds);

        return badgeEntities.stream()
                .map(badgeEntity -> {
                    // 뱃지 ID가 3일치하면서 대표뱃지가 True인 항목이 있는지 확인
                    boolean isRepresentative = designerBadges.stream()
                            .anyMatch(designerBadgeEntity -> designerBadgeEntity.getBadgeId().equals(badgeEntity.getId()) && designerBadgeEntity.isRepresentativeBadge());
                    return Badge.builder()
                            .badgeId(badgeEntity.getId())
                            .badgeName(badgeEntity.getBadgeName())
                            .badgeContent(badgeEntity.getBadgeContent())
                            .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                            .isRepresentativeBadge(isRepresentative)
                            .badgeColor(badgeEntity.getBadgeColor())
                            .build();
                })
                .toList();
    }

    @Override
    public Designer getAllDesignerDataByDesignerId(Long userId) {
        Designer designer = designerRepository.findById(userId)
                .map(DesignerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        List<License> licenses = Optional.ofNullable(licenseRepository.findByDesignerId(userId))
                .map(DesignerMapper::toLicenses)
                .orElse(Collections.emptyList());
        List<Badge> badges = getRepresentativeBadges(userId);

        designer.updateLicenses(licenses);
        designer.updateBadges(badges);
        return designer;
    }

    @Override
    public Designer.Profile getDesignerProfileByDesignerId(Long designerId) {
        Designer designer = getAllDesignerDataByDesignerId(designerId);
        Workspace workspace = workspacePort.getByDesignerId(designerId);
        return designer.getProfile(workspace);

    }
}
