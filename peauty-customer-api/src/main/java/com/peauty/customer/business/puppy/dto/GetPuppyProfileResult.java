package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Puppy;

import java.util.List;

public record GetPuppyProfileResult(
        Long puppyId,
        String name,
        String breed,
        Long weight,
        String sex,
        Integer age,
        List<String> disease,
        String puppyProfileImageUrl
) {
    public static GetPuppyProfileResult from(Puppy puppy){
        return new GetPuppyProfileResult(
                puppy.getPuppyId(),
                puppy.getName(),
                puppy.getBreed().name(),
                puppy.getWeight(),
                puppy.getSex().name(),
                puppy.getAge(),
                puppy.getDisease().stream().map(Disease::name).toList(),
                puppy.getProfileImageUrl()
        );
    }
}
