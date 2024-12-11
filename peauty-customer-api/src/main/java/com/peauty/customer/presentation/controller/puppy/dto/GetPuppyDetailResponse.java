package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.GetPuppyDetailResult;

import java.time.LocalDate;
import java.util.List;

public record GetPuppyDetailResponse(
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

    public static GetPuppyDetailResponse from(GetPuppyDetailResult result){
        return new GetPuppyDetailResponse(
                result.puppyId(),
                result.name(),
                result.breed(),
                result.weight(),
                result.sex(),
                result.age(),
                result.formattedAge(),
                result.birthdate(),
                result.detail(),
                result.disease(),
                result.diseaseDescription(),
                result.profileImageUrl(),
                result.puppySize()
        );
    }
}
