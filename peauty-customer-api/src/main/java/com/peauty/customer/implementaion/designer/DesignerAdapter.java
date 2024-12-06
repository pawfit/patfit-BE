package com.peauty.customer.implementaion.designer;

import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.domain.designer.Badge;
import com.peauty.persistence.designer.*;
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
                        .build())
                .collect(Collectors.toList());
    }

}
