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
                .puppyAgeInfo(new PuppyAgeInfo(birthdate))
                .detail(detail)
                .diseases(disease)
                .diseaseDescription(diseaseDescription)
                .profileImageUrl(profileImageUrl)
                .puppySize(puppySize)
                .build();
    }
}
