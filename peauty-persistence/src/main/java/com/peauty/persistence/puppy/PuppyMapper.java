package com.peauty.persistence.puppy;

import com.peauty.domain.puppy.Puppy;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.puppy.PuppyEntity;

public class PuppyMapper {

    private PuppyMapper() {}

    public static Puppy toDomain(PuppyEntity puppyEntity) {
        return Puppy.builder()
                .puppyId(puppyEntity.getId())
                .name(puppyEntity.getName())
                .breed(puppyEntity.getBreed())
                .weight(puppyEntity.getWeight())
                .sex(puppyEntity.getSex())
                .age(puppyEntity.getAge())
                .birthdate(puppyEntity.getBirthdate())
                .detail(puppyEntity.getDetail())
                .disease(puppyEntity.getDisease())
                .diseaseDescription(puppyEntity.getDiseaseDescription())
                .profileImageUrl(puppyEntity.getProfileImageUrl())
                .customerId(puppyEntity.getCustomer().getId()) // customerId 추가
                .puppySize(puppyEntity.getPuppySize())
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
                .puppySize(puppy.getPuppySize())
                .build();

    }
}
