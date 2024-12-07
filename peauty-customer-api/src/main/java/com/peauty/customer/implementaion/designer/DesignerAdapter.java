package com.peauty.customer.implementaion.designer;

import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.domain.designer.Badge;
import com.peauty.persistence.designer.badge.BadgeEntity;
import com.peauty.persistence.designer.badge.BadgeRepository;
import com.peauty.persistence.designer.badge.DesignerBadgeEntity;
import com.peauty.persistence.designer.badge.DesignerBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DesignerAdapter implements DesignerPort {

    private final BadgeRepository badgeRepository;
    private final DesignerBadgeRepository designerBadgeRepository;

    // 대표뱃지 조회
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
                    // 뱃지 ID가 일치하면서 대표뱃지가 True인 항목이 있는지 확인
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



}
