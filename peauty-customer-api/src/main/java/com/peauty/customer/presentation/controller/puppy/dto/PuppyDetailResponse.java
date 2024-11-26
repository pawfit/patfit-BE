package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.PuppyResult;

import java.time.LocalDate;
import java.util.List;

public record PuppyDetailResponse(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        Integer age,
        LocalDate birthdate,
        String detail,
        List<String> disease,

        String diseaseDescription,

        String profileImageUrl
) {
    public static PuppyDetailResponse from(PuppyResult result) {
        return new PuppyDetailResponse(
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
                result.profileImageUrl()
        );
    }
}