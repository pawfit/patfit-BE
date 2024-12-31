package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.GetDesignerAccountResult;

public record GetDesignerAccountResponse(
        Long designerId,
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        String phoneNumber
) {

    public static GetDesignerAccountResponse from(GetDesignerAccountResult result) {
        return new GetDesignerAccountResponse(
                result.designerId(),
                result.name(),
                result.nickname(),
                result.profileImageUrl(),
                result.email(),
                result.phoneNumber()
        );
    }
}
