package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.GetPuppyProfilesResult;

import java.util.List;

public record GetPuppyProfilesResponse(
        Long customerId,
        String customerNickname,
        String customerImageUrl,
        List<GetPuppyProfileResponse> puppies
) {
    public static GetPuppyProfilesResponse from(GetPuppyProfilesResult result) {
        List<GetPuppyProfileResponse> puppyResponses = result.puppies().stream()
                .map(GetPuppyProfileResponse::from)
                .toList();
        return new GetPuppyProfilesResponse(
                result.customerId(),
                result.nickname(),
                result.customerImageUrl(),
                puppyResponses
        );
    }
}