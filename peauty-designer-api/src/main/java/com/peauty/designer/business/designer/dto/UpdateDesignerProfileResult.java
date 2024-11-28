package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Designer;

public record UpdateDesignerProfileResult(
        Long designerId,
        String name,
        String nickname,
        String phoneNumber,
        String address,
        String profileImageUrl,
        String email
) {
    public static UpdateDesignerProfileResult from(Designer designer){
        return new UpdateDesignerProfileResult(
                designer.getDesignerId(),
                designer.getName(),
                designer.getNickname(),
                designer.getPhoneNumber(),
                designer.getAddress(),
                designer.getProfileImageUrl(),
                designer.getEmail()
        );
    }

}
