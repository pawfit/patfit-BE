package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.GetPuppyProfileResult;

import java.util.List;

public record GetPuppyProfileResponse(
        Long customerId,
        String nickname,
        String customerImageUrl,
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        Integer age,
        List<String> disease,
        String puppyProfileImageUrl
) {

    public static GetPuppyProfileResponse from(GetPuppyProfileResult result){
        return new GetPuppyProfileResponse(
                result.customerId(),
                result.nickname(),
                result.customerImageUrl(),
                result.puppyId(),
                result.name(),
                result.breed(),
                result.weight(),
                result.sex(),
                result.age(),
                result.disease(),
                result.puppyProfileImageUrl()
        );
    }

}
