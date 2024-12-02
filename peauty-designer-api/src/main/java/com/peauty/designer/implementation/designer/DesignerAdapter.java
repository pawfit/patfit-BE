package com.peauty.designer.implementation.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
import com.peauty.persistence.designer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DesignerAdapter implements DesignerPort {

    private final DesignerRepository designerRepository;
    private final LicenseRepository licenseRepository;
    private final DesignerBadgeRepository designerBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final RatingRepository ratingRepository;

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        if (designerRepository.existsByNickname(nickname)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_USER);
        }
    }

    @Override
    public void checkCustomerPhoneNumDuplicated(String phoneNum) {
        if (designerRepository.existsByPhoneNumber(phoneNum)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_PHONE_NUM);
        }
    }

    @Override
    public Optional<Designer> findBySocialId(String socialId) {
        return Optional.ofNullable(designerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(DesignerMapper::toDesignerDomain);
    }

    @Override
    public Designer save(Designer designer) {
        DesignerEntity designerEntityToSave = DesignerMapper.toEntity(designer);
        List<LicenseEntity> licenseEntityToSave = DesignerMapper.toLicenseEntity(designer);

        DesignerEntity savedDesignerEntity = designerRepository.save(designerEntityToSave);
        List<LicenseEntity> savedLicenseEntity = licenseRepository.saveAll(licenseEntityToSave);

        return DesignerMapper.toDesignerAndLicenseDomain(savedDesignerEntity, savedLicenseEntity);
    }

    @Override
    public Designer registerNewDesigner(SignUpCommand command) {
        Designer designerToSave = Designer.builder()
                .designerId(0L)
                .socialId(command.socialId())
                .socialPlatform(command.socialPlatform())
                .name(command.name())
                .phoneNumber(command.phoneNumber())
                .email(command.email())
                .status(Status.ACTIVE)
                .role(Role.ROLE_DESIGNER)
                .nickname(command.nickname())
                .profileImageUrl(command.profileImageUrl())
                .build();
        return save(designerToSave);
    }

    @Override
    public Designer getByDesignerId(Long designerId) {
        return designerRepository.findById(designerId)
                .map(DesignerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }

    @Override
    public Designer getAllDesignerDataByDesignerId(Long userId) {
        Designer designer = designerRepository.findById(userId)
                .map(DesignerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        List<License> licenses = Optional.ofNullable(licenseRepository.findByDesignerId(userId))
                .map(DesignerMapper::toLicenses)
                .orElse(Collections.emptyList());

        List<Long> badgeIds = designerBadgeRepository.findRepresentativeBadgeIdsByDesignerId(userId);
        List<BadgeEntity> badgeEntities = badgeRepository.findAllById(badgeIds);
        List<Badge> badges = badgeEntities.stream()
                .map(badgeEntity -> Badge.builder()
                        .badgeId(badgeEntity.getId())
                        .badgeName(badgeEntity.getBadgeName())
                        .badgeContent(badgeEntity.getBadgeContent())
                        .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                        .isRepresentativeBadge(true)
                        .build())
                .collect(Collectors.toList());

        designer.updateLicenses(licenses);
        designer.updateBadges(badges);
        return designer;
    }
}
