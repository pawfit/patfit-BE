package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.*;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyDetailResult(
        Long puppyId,
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
    public static UpdatePuppyDetailResult from(Puppy puppy) {
        return new UpdatePuppyDetailResult(
                puppy.getPuppyId(),
                puppy.getName(),
                puppy.getBreed(),
                puppy.getWeight(),
                puppy.getSex(),
                puppy.getAge(),
                puppy.getBirthdate(),
                puppy.getDetail(),
                puppy.getDisease(),
                puppy.getDiseaseDescription(),
                puppy.getProfileImageUrl(),
                puppy.getPuppySize()
        );
    }

}
