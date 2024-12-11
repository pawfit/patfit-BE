package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.PuppySize;
import com.peauty.domain.puppy.Sex;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyDetailRequest(
        String name,
        String breed,
        Long weight,
        Sex sex,
        LocalDate birthdate,
        String detail,
        List<String> disease,
        String diseaseDescription,
        String profileImageUrl,
        String puppySize
) {
    public UpdatePuppyDetailCommand toCommand(){
        return new UpdatePuppyDetailCommand(
                this.name,
                Breed.from(this.breed),
                this.weight,
                this.sex,
                this.birthdate,
                this.detail,
                this.disease.stream().map(Disease::from).toList(),
                this.diseaseDescription,
                this.profileImageUrl,
                PuppySize.from(this.puppySize)
        );
    }
}
