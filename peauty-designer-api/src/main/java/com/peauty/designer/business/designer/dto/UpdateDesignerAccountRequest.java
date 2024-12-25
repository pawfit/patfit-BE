package com.peauty.designer.business.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateDesignerAccountRequest(

        @Schema(description = "디자이너 이름", example = "John Doe")
        String name,

        @Schema(description = "디자이너 닉네임", example = "johnny")
        String nickname,

        @Schema(description = "디자이너 전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "디자이너 주소", example = "서울특별시 강남구 테헤란로 123")
        String address,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/images/profile.jpg")
        String profileImageUrl,

        @Schema(description = "디자이너 이메일", example = "johndoe@example.com")
        String email
) {
    public UpdateDesignerAccountCommand toCommand() {
        return new UpdateDesignerAccountCommand(
                this.name,
                this.nickname,
                this.phoneNumber,
                this.address,
                this.profileImageUrl,
                this.email
        );
    }
}
