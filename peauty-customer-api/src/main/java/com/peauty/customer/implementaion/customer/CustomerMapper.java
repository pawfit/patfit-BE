package com.peauty.customer.implementaion.customer;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.*;
import com.peauty.domain.user.Role;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.designer.DesignerEntity;
import com.peauty.persistence.designer.LicenseEntity;
import com.peauty.persistence.designer.RatingEntity;
import com.peauty.persistence.designer.WorkspaceEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerMapper {

    private CustomerMapper() {
        // private 생성자로 인스턴스화 방지
    }

    public static Customer toDomain(CustomerEntity customerEntity) {
        return Customer.builder()
                .customerId(customerEntity.getId())
                .socialId(customerEntity.getSocialId())
                .socialPlatform(customerEntity.getSocialPlatform())
                .name(customerEntity.getName())
                .phoneNumber(customerEntity.getPhoneNum())
                .status(customerEntity.getStatus())
                .role(Role.ROLE_CUSTOMER)
                .nickname(customerEntity.getNickname())
                .address(customerEntity.getAddress())
                .profileImageUrl(customerEntity.getProfileImageUrl())
                .build();
    }

    public static CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getCustomerId())
                .socialId(customer.getSocialId())
                .socialPlatform(customer.getSocialPlatform())
                .name(customer.getName())
                .phoneNum(customer.getPhoneNumber())
                .status(customer.getStatus())
                .nickname(customer.getNickname())
                .address(customer.getAddress())
                .profileImageUrl(customer.getProfileImageUrl())
                .build();
    }
    // 엔티티 -> 도메인
    public static Designer toDesignerDomain(DesignerEntity entity) {
        return Designer.builder()
                .designerId(entity.getId())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .profileImageUrl(entity.getProfileImageUrl())
                .yearOfExperience(entity.getYearsOfExperience())
                .build();
    }
    // 엔티티 -> 도메인
    public static Workspace toWorkspaceDomain(WorkspaceEntity entity) {
        return Workspace.builder()
                .workspaceId(entity.getId())
                .designerId(entity.getDesignerId())
                .workspaceName(entity.getWorkspaceName())
                .address(entity.getAddress())
                .addressDetail(entity.getAddressDetail())
                .bannerImageUrl(entity.getBannerImageUrl())
                .reviewCount(entity.getReviewCount())
                .reviewRating(entity.getReviewRating())
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


    public static Rating toRatingDomain(RatingEntity rating) {
        if (rating == null) {
            return Rating.builder()
                    .ratingId(0L)
                    .totalScore(0.0)
                    .scissors(Scissors.NONE)
                    .build();
        }

        return Rating.builder()
                .ratingId(rating.getId())
                .totalScore(rating.getTotalScore())
                .scissors(rating.getScissors())
                .build();
    }

}
