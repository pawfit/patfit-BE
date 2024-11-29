package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.PuppySize;
import com.peauty.domain.puppy.Sex;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyDetailCommand(
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
}
