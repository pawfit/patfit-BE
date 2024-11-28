package com.peauty.designer.business.designer.dto;

public record UpdateDesignerProfileResponse(
        Long designerId,
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl,
        String email
) {
    public static UpdateDesignerProfileResponse from(UpdateDesignerProfileResult result){
        return new UpdateDesignerProfileResponse(
                result.designerId(),
                result.name(),
                result.nickname(),
                result.phoneNumber(),
                result.address(),
                result.profileImageUrl(),
                result.email()
        );
    }
}
