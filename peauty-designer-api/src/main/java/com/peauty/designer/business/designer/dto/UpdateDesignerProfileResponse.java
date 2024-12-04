package com.peauty.designer.business.designer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateDesignerProfileResponse(
        @Schema(description = "디자이너 ID", example = "1")
        Long designerId,

        @Schema(description = "디자이너 이름", example = "John Doe")
        String name,

        @Schema(description = "디자이너 닉네임", example = "johnny")
        String nickname,

        @Schema(description = "디자이너 전화번호", example = "01012345678")
        String phoneNum,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/images/profile.jpg")
        String profileImageUrl,

        @Schema(description = "디자이너 이메일", example = "johndoe@example.com")
        String email
) {
    public static UpdateDesignerProfileResponse from(UpdateDesignerProfileResult result) {
        return new UpdateDesignerProfileResponse(
                result.designerId(),
                result.name(),
                result.nickname(),
                result.phoneNumber(),
                result.profileImageUrl(),
                result.email()
        );
    }
}
