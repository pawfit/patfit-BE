package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.PuppySize;
import com.peauty.domain.puppy.Sex;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyDetailResponse(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        Sex sex,
        Integer age,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize
) {

    public static UpdatePuppyDetailResponse from(UpdatePuppyDetailResult result){
        return new UpdatePuppyDetailResponse(
                result.puppyId(),
                result.name(),
                result.breed(),
                result.weight(),
                result.sex(),
                result.age(),
                result.birthdate(),
                result.detail(),
                result.disease(),
                result.diseaseDescription(),
                result.profileImageUrl(),
                result.puppySize()
        );
    }
}
