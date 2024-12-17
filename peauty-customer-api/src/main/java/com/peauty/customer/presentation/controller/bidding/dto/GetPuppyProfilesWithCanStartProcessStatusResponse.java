package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetPuppyProfilesWithCanStartProcessStatusResult;
import com.peauty.domain.puppy.Puppy;

import java.util.List;

public record GetPuppyProfilesWithCanStartProcessStatusResponse(
        List<Puppy.PuppyProfile> puppies
) {

    public static GetPuppyProfilesWithCanStartProcessStatusResponse from(GetPuppyProfilesWithCanStartProcessStatusResult result) {
        return new GetPuppyProfilesWithCanStartProcessStatusResponse(result.puppies());
    }
}
