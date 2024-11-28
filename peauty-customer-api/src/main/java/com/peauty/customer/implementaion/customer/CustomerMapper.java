package com.peauty.customer.implementaion.customer;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.user.Role;
import com.peauty.persistence.customer.CustomerEntity;

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
}
