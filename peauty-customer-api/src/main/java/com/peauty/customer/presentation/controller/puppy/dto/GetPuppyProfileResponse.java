package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.GetPuppyProfileResult;

import java.util.List;

public record GetPuppyProfileResponse(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
//        String formattedAge,
        Integer age,
        List<String> disease,
        String puppyProfileImageUrl
) {
    public static GetPuppyProfileResponse from(GetPuppyProfileResult result){
        return new GetPuppyProfileResponse(
                result.puppyId(),
                result.name(),
                result.breed(),
                result.weight(),
                result.sex(),
                result.age(),
//                result.formattedAge(),
                result.disease(),
                result.puppyProfileImageUrl()
        );
    }
}
