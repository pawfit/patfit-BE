package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Puppy;

import java.time.LocalDate;
import java.util.List;

public record RegisterPuppyResult(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize,
        Integer age,
        String formattedAge
) {
    public static RegisterPuppyResult from(Puppy puppy) {
        return new RegisterPuppyResult(
                puppy.getPuppyId(),
                puppy.getName(),
                puppy.getBreed().getBreedName(),
                puppy.getWeight(),
                puppy.getSex().name(),
                puppy.getPuppyAgeInfo().getBirthdate(),
                puppy.getDetail(),
                puppy.getDisease().stream().map(Disease::getDescription).toList(),
                puppy.getDiseaseDescription(),
                puppy.getProfileImageUrl(),
                puppy.getPuppySize().getDescription(),
                puppy.getPuppyAgeInfo().getSimpeAge(),
                puppy.getPuppyAgeInfo().getFormattedAge()
        );
    }
}