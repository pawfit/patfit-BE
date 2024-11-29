package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.*;

import java.time.LocalDate;
import java.util.List;

public record RegisterPuppyCommand(
        Long userId,
        String name,
        Breed breed,
        Long weight,
        Sex sex,
        Integer age,
        LocalDate birthdate,
        String detail,
        List<Disease> disease,
        String diseaseDescription,
        String profileImageUrl,
        PuppySize puppySize
) {
    public Puppy toDomain() {
        return Puppy.builder()
                .name(name)
                .breed(breed)
                .weight(weight)
                .sex(sex)
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
