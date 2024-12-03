package com.peauty.customer.business.puppy.dto;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.puppy.Puppy;

import java.util.List;

public record GetPuppyProfilesResult(
        Long customerId,
        String nickname,
        String customerImageUrl,
        List<GetPuppyProfileResult> puppies
) {
    public static GetPuppyProfilesResult from(Customer customer, List<Puppy> puppies){
        List<GetPuppyProfileResult> getPuppyResults = puppies.stream()
                .map(GetPuppyProfileResult::from)
                .toList();
        return new GetPuppyProfilesResult(
                customer.getCustomerId(),
                customer.getNickname(),
                customer.getProfileImageUrl(),
                getPuppyResults
        );
    }

}
