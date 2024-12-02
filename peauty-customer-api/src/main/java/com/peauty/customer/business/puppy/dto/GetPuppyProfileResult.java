package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Puppy;

import java.util.List;

public record GetPuppyProfileResult(
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
    public static GetPuppyProfileResult from(Customer customer, Puppy puppy){
        return new GetPuppyProfileResult(
                customer.getCustomerId(),
                customer.getNickname(),
                customer.getProfileImageUrl(),
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
