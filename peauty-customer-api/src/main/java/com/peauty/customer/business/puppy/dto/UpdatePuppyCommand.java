package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.*;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyCommand(
        Long userId,
        Long puppyId,
        String name,
        Breed breed,
        Sex sex,
        Long weight,
        Integer age,
        LocalDate birthdate,
        String detail,
        List<Disease> disease,
        String diseaseDescription,
        String profileImageUrl,
        PuppySize puppySize
) {
    public PuppyProfile toPuppyProfile() {
        return PuppyProfile.builder()
                .name(name)
                .breed(breed)
                .sex(sex)
                .weight(weight)
                .age(age)
                .birthdate(birthdate)
                .detail(detail)
                .disease(disease)
                .diseaseDescription(diseaseDescription)
                .profileImageUrl(profileImageUrl)
                .puppySize(puppySize)
                .build();
    }
}