package com.peauty.designer.business.designer.dto;

public record UpdateDesignerProfileRequest(
        String name,
        String nickname,
        String phoneNum,
        String address,
        String profileImageUrl,
        String email
) {
    public UpdateDesignerProfileCommand toCommand(){
        return new UpdateDesignerProfileCommand(
                this.name,
                this.nickname,
                this.phoneNum,
                this.address,
                this.profileImageUrl,
                this.email
        );
    }
}
