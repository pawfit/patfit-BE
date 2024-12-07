package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Designer;

public record GetDesignerAccountResult(
        Long designerId,
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        String phoneNumber
) {

    public static GetDesignerAccountResult from(Designer designer){
        return new GetDesignerAccountResult(
                designer.getDesignerId(),
                designer.getName(),
                designer.getNickname(),
                designer.getProfileImageUrl(),
                designer.getEmail(),
                designer.getPhoneNumber()
        );
    }
}
