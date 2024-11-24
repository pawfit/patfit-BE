package com.peauty.customer.presentation.controller.auth.dto;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequest(
        @Schema(description = "사용자의 소셜 로그인 플랫폼에서의 소셜 ID", example = "313124")
        String socialId,
        @Schema(description = "사용자의 소셜 로그인 플랫폼", example = "KAKAO, GOOGLE, APPLE")
        SocialPlatform socialPlatform,
        @Schema(description = "사용자의 이름", example = "홍길동")
        String name,
        @Schema(description = "사용자의 전화번호", example = "010-1234-5678")
        String phoneNum,
        @Schema(description = "사용자의 주소", example = "서울시 강남구 선릉동")
        String address,
        @Schema(description = "사용자의 닉네임", example = "MrHong")
        String nickname,
        @Schema(description = "사용자의 프로필 이미지 url", example = "https://kakaosocial/default-image")
        String profileImageUrl
) {

    public SignUpCommand toCommand() {
        return new SignUpCommand(
                this.socialId,
                this.socialPlatform,
                this.name,
                this.phoneNum,
                this.address,
                this.nickname,
                this.profileImageUrl
        );
    }
}
