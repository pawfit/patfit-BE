package com.peauty.customer.implementaion.customer;

import com.peauty.domain.user.Role;
import com.peauty.domain.user.User;
import com.peauty.persistence.customer.CustomerEntity;

public class CustomerMapper {

    private CustomerMapper() {
        // private 생성자로 인스턴스화 방지
    }

    public static User toDomain(CustomerEntity customerEntity) {
        return new User(
                customerEntity.getId(),
                customerEntity.getOidcProviderId(),
                customerEntity.getOidcProviderType(),
                customerEntity.getName(),
                customerEntity.getNickname(),
                customerEntity.getPhoneNum(),
                customerEntity.getProfileImageUrl(),
                customerEntity.getStatus(),
                Role.ROLE_CUSTOMER
        );
    }

    public static CustomerEntity toEntity(User user) {
        return CustomerEntity.builder()
                .id(user.id())
                .oidcProviderId(user.oidcProviderId())
                .oidcProviderType(user.oidcProviderType())
                .name(user.name())
                .nickname(user.nickname())
                .phoneNum(user.phoneNum())
                .profileImageUrl(user.profileImageUrl())
                .status(user.status())
                .build();
    }
}
