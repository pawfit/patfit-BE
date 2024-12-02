package com.peauty.designer.implementation.designer;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.domain.user.Role;
import com.peauty.persistence.designer.DesignerEntity;
import com.peauty.persistence.designer.LicenseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DesignerMapper {

    private DesignerMapper() {
        // private 생성자로 인스턴스화 방지
    }

    public static Designer toDesignerAndLicenseDomain(DesignerEntity designerEntity, List<LicenseEntity> licenseEntities) {
        return Designer.builder()
                .designerId(designerEntity.getId())
                .socialId(designerEntity.getSocialId())
                .socialPlatform(designerEntity.getSocialPlatform())
                .name(designerEntity.getName())
                .phoneNumber(designerEntity.getPhoneNum())
                .email(designerEntity.getEmail())
                .status(designerEntity.getStatus())
                .role(Role.ROLE_DESIGNER)
                .nickname(designerEntity.getNickname())
                .address(designerEntity.getAddress())
                .profileImageUrl(designerEntity.getProfileImageUrl())
                .licenses(Optional.ofNullable(licenseEntities)
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(licenseEntity -> License.builder()
                                .licenseId(licenseEntity.getId())
                                .licenseImageUrl(licenseEntity.getLicenseImageUrl())
                                .build()
                        ).collect(Collectors.toList())
                ).build();
    }

    public static Designer toDesignerDomain(DesignerEntity designerEntity) {
        return Designer.builder()
                .designerId(designerEntity.getId())
                .socialId(designerEntity.getSocialId())
                .socialPlatform(designerEntity.getSocialPlatform())
                .name(designerEntity.getName())
                .phoneNumber(designerEntity.getPhoneNumber())
                .email(designerEntity.getEmail())
                .status(designerEntity.getStatus())
                .role(Role.ROLE_DESIGNER)
                .nickname(designerEntity.getNickname())
                .profileImageUrl(designerEntity.getProfileImageUrl())
                .build();
    }


    public static DesignerEntity toEntity(Designer designer) {
        return DesignerEntity.builder()
                .id(designer.getDesignerId())
                .socialId(designer.getSocialId())
                .socialPlatform(designer.getSocialPlatform())
                .name(designer.getName())
                .phoneNumber(designer.getPhoneNumber())
                .email(designer.getEmail())
                .status(designer.getStatus())
                .nickname(designer.getNickname())
                .profileImageUrl(designer.getProfileImageUrl())
                .build();
    }

    public static List<License> toLicenses(List<LicenseEntity> licenseEntities) {
        return Optional.ofNullable(licenseEntities)
                .orElse(Collections.emptyList()) // Null일 경우 빈 리스트 반환
                .stream()
                .filter(Objects::nonNull) // 리스트 내부 요소도 null 체크
                .map(licenseEntity -> License.builder()
                        .licenseId(licenseEntity.getId())
                        .licenseImageUrl(licenseEntity.getLicenseImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<LicenseEntity> toLicenseEntity(Designer designer) {
        List<License> licenses = designer.getLicenses();
        if (licenses == null || licenses.isEmpty()) {
            return Collections.emptyList();
        }

        return licenses.stream()
                .map(license -> LicenseEntity.builder()
                        .licenseImageUrl(license.getLicenseImageUrl()) // License 이미지 URL 설정
                        .designerId(designer.getDesignerId()) // Designer의 ID 설정
                        .build())
                .collect(Collectors.toList());
    }
}
