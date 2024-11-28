package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.GetDesignerProfileResult;

public record GetDesignerProfileResponse(
        Long designerId,
        String name,
        String nickname,
        String profileImageUrl,
        String address,
        String email,
        String phoneNum
) {

    public static GetDesignerProfileResponse from(GetDesignerProfileResult result) {
        return new GetDesignerProfileResponse(
                result.designerId(),
                result.name(),
                result.nickname(),
                result.profileImageUrl(),
                result.address(),
                result.email(),
                result.phoneNum()
        );
    }
}
