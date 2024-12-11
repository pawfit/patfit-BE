package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Puppy;

import java.time.LocalDate;
import java.util.List;

public record GetPuppyDetailResult(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        Integer age,
        String formattedAge,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize
) {
    public static GetPuppyDetailResult from(Puppy puppy){
        return new GetPuppyDetailResult(
                puppy.getPuppyId(),
                puppy.getName(),
                puppy.getBreed().getBreedName(),
                puppy.getWeight(),
                puppy.getSex().name(),
                puppy.getPuppyAgeInfo().getYears(),
                puppy.getPuppyAgeInfo().getFormattedAge(),
                puppy.getPuppyAgeInfo().getBirthdate(),
                puppy.getDetail(),
                puppy.getDisease().stream().map(Disease::getDescription).toList(),
                puppy.getDiseaseDescription(),
                puppy.getProfileImageUrl(),
                puppy.getPuppySize().getDescription()
        );
    }
}
