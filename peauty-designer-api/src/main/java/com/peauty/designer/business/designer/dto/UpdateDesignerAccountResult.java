package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.Designer;

public record UpdateDesignerAccountResult(
        Long designerId,
        String name,
        String nickname,
        String phoneNumber,
        String profileImageUrl,
        String email
) {
    public static UpdateDesignerAccountResult from(Designer designer){
        return new UpdateDesignerAccountResult(
                designer.getDesignerId(),
                designer.getName(),
                designer.getNickname(),
                designer.getPhoneNumber(),
                designer.getProfileImageUrl(),
                designer.getEmail()
        );
    }

}
