package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Designer;

public record GetDesignerProfileResult(
        Long designerId,
        String name,
        String nickname,
        String profileImageUrl,
        String address,
        String email,
        String phoneNum
) {

    public static GetDesignerProfileResult from(Designer designer){
        return new GetDesignerProfileResult(
                designer.getDesignerId(),
                designer.getName(),
                designer.getNickname(),
                designer.getProfileImageUrl(),
                designer.getAddress(),
                designer.getEmail(),
                designer.getPhoneNumber()
        );
    }
}
