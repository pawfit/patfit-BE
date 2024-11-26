package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.puppy.Sex;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyCommand(
        Long userId,
        Long puppyId,
        String name,
        Breed breed,
        Sex sex,
        Long weight,
        Integer age,
        LocalDate birthdate,
        String detail,
        List<Disease> disease,
        String diseaseDescription,
        String profileImageUrl
) {
    public Puppy updateDomain(Puppy existingPuppy) {
        return Puppy.builder()
                .puppyId(existingPuppy.getPuppyId())
                .name(defaultIfNull(this.name, existingPuppy.getName()))
                .breed(defaultIfNull(this.breed, existingPuppy.getBreed()))
                .sex(defaultIfNull(this.sex, existingPuppy.getSex()))
                .weight(defaultIfNull(this.weight, existingPuppy.getWeight()))
                .age(defaultIfNull(this.age, existingPuppy.getAge()))
                .birthdate(defaultIfNull(this.birthdate, existingPuppy.getBirthdate()))
                .detail(defaultIfNull(this.detail, existingPuppy.getDetail()))
                .disease(defaultIfNull(this.disease, existingPuppy.getDisease()))
                .diseaseDescription(defaultIfNull(this.diseaseDescription, existingPuppy.getDiseaseDescription()))
                .profileImageUrl(defaultIfNull(this.profileImageUrl, existingPuppy.getProfileImageUrl()))
                .build();
    }

    private <T> T defaultIfNull(T newValue, T existingValue) {
        return newValue != null ? newValue : existingValue;
    }
}