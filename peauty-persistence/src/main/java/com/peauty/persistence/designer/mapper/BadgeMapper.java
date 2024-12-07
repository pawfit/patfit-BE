package com.peauty.persistence.designer.mapper;

import com.peauty.domain.designer.Badge;
import com.peauty.persistence.designer.badge.BadgeEntity;
import com.peauty.persistence.designer.badge.DesignerBadgeEntity;

public class BadgeMapper {

    public BadgeMapper(){
        // private 생성자로 인스턴스화 방지
    }

    // 엔티티 -> 도메인
    public static Badge toBadgeDomain(BadgeEntity entity) {
        return Badge.builder()
                .badgeId(entity.getId())
                .badgeName(entity.getBadgeName())
                .badgeContent(entity.getBadgeContent())
                .badgeImageUrl(entity.getBadgeImageUrl())
                .badgeColor(entity.getBadgeColor())
                .build();
    }

    // 도메인 -> 엔티티
    public static BadgeEntity toBadgeEntity(Badge domain) {
        return BadgeEntity.builder()
                .id(domain.getBadgeId())
                .badgeName(domain.getBadgeName())
                .badgeContent(domain.getBadgeContent())
                .badgeImageUrl(domain.getBadgeImageUrl())
                .badgeColor(domain.getBadgeColor())
                .build();
    }

    // 엔티티 -> 도메인
    public static Badge toDesignerBadgeDomain(DesignerBadgeEntity entity) {
        return Badge.builder()
                .badgeId(entity.getBadgeId())
                .isRepresentativeBadge(entity.isRepresentativeBadge())
                .build();
    }

    // 도메인 -> 엔티티
    public static DesignerBadgeEntity toDesignerBadgeEntity(Badge domain, Long designerId) {
        return DesignerBadgeEntity.builder()
                .designerId(designerId)
                .badgeId(domain.getBadgeId())
                .isRepresentativeBadge(domain.getIsRepresentativeBadge())
                .build();
    }

    // 기존 엔티티를 업데이트하는 메서드
    public static DesignerBadgeEntity updateEntity(DesignerBadgeEntity Entity, Badge badge) {
        return DesignerBadgeEntity.builder()
                .id(Entity.getId()) // ID 유지
                .designerId(Entity.getDesignerId()) // 디자이너 ID 유지
                .badgeId(Entity.getBadgeId()) // 뱃지 ID 유지
                .isRepresentativeBadge(badge.getIsRepresentativeBadge()) // 대표 뱃지 업데이트
                .build();
    }


}


