package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.RegisterPuppyResult;

import java.time.LocalDate;
import java.util.List;

public record RegisterPuppyResponse(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        String formattedAge,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize
) {
    public static RegisterPuppyResponse from(RegisterPuppyResult result){
        return new RegisterPuppyResponse(
                result.puppyId(),
                result.name(),
                result.breed(),
                result.weight(),
                result.sex(),
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
