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
                customerEntity.getSocialId(),
                customerEntity.getSocialPlatform(),
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
                .id(user.getUserId())
                .socialId(user.getSocialId())
                .socialPlatform(user.getSocialPlatform())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNum(user.getPhoneNum())
                .profileImageUrl(user.getProfileImageUrl())
                .status(user.getStatus())
                .build();
    }
}
