package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.*;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyDetailResult(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        Sex sex,
        String formattedAge,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize
) {
    public static UpdatePuppyDetailResult from(Puppy puppy) {
        return new UpdatePuppyDetailResult(
                puppy.getPuppyId(),
                puppy.getName(),
                puppy.getBreed().getBreedName(),
                puppy.getWeight(),
                puppy.getSex(),
                puppy.getPuppyAgeInfo().getFormattedAge(),
                puppy.getPuppyAgeInfo().getBirthdate(),
                puppy.getDetail(),
                puppy.getDiseases().stream().map(Disease::getDescription).toList(),
                puppy.getDiseaseDescription(),
                puppy.getProfileImageUrl(),
                puppy.getPuppySize().getDescription()
        );
    }

}
