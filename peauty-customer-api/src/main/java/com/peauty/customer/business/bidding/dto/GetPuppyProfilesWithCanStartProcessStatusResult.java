package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.puppy.Puppy;

import java.util.List;

public record GetPuppyProfilesWithCanStartProcessStatusResult(
        List<Puppy.PuppyProfile> puppies
) {

    public static GetPuppyProfilesWithCanStartProcessStatusResult from(List<Puppy.PuppyProfile> puppies) {
        return new GetPuppyProfilesWithCanStartProcessStatusResult(puppies);
    }
}
