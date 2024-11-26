package com.peauty.customer.implementaion.puppy;

import com.peauty.domain.puppy.Puppy;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.puppy.PuppyEntity;

public class PuppyMapper {

    private PuppyMapper() {}

    public static Puppy toDomain(PuppyEntity entity) {
        return Puppy.builder()
                .puppyId(entity.getId())
                .name(entity.getName())
                .breed(entity.getBreed())
                .weight(entity.getWeight())
                .sex(entity.getSex())
                .age(entity.getAge())
                .birthdate(entity.getBirthdate())
                .detail(entity.getDetail())
                .disease(entity.getDisease())
                .diseaseDescription(entity.getDiseaseDescription())
                .profileImageUrl(entity.getProfileImageUrl())
                .customerId(entity.getCustomer().getId()) // customerId 추가
                .build();
    }

    public static PuppyEntity toEntity(Puppy puppy, CustomerEntity customer) {

        return PuppyEntity.builder()
                .id(puppy.getPuppyId())
                .name(puppy.getName())
                .breed(puppy.getBreed())
                .weight(puppy.getWeight())
                .sex(puppy.getSex())
                .age(puppy.getAge())
                .birthdate(puppy.getBirthdate())
                .detail(puppy.getDetail())
                .disease(puppy.getDisease())
                .diseaseDescription(puppy.getDiseaseDescription())
                .profileImageUrl(puppy.getProfileImageUrl())
                .customer(customer) // CustomerEntity를 설정
                .build();

    }
}

