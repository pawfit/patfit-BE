package com.peauty.customer.presentation.controller.auth;

import com.peauty.customer.business.auth.AuthService;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.response.PeautyApiResponse;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Auth Test", description = "Auth Test API")
@RequestMapping("/v1/auth/test")
@RequiredArgsConstructor
public class AuthTestController {

    private final AuthService authService;

    @PostMapping("/sign")
    @Operation(summary = "고객 테스터 로그인&회원가입", description = "고객 테스터의 로그인&회원가입 통합 진입점입니다.")
    public SignUpResponse signTester(
            @RequestParam SocialPlatform socialPlatform,
            @RequestParam String idToken,
            @RequestParam String nickname,
            @RequestParam String phoneNumber
    ) {
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNumber);
        return SignUpResponse.from(result);
    }

    @GetMapping("/signin")
    @Hidden
    public PeautyApiResponse<String> testSignIn(
            @RequestParam("accessToken") String accessToken,
            @RequestParam("refreshToken") String refreshToken
    ) {
        return PeautyApiResponse.ok("accessToken : " + accessToken + "\n" +  "refreshToken : " + refreshToken);
    }

    @GetMapping("/signup")
    @Hidden
    public PeautyApiResponse<String> testSignUp(
            @RequestParam("socialId") String socialId,
            @RequestParam("socialPlatform") SocialPlatform socialPlatform,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImageUrl") String profileImageUrl
    ) {
        String response = String.format("""
           socialId: %s
           socialPlatform: %s
           nickname: %s
           profileImageUrl: %s
           """,
                socialId, socialPlatform, nickname, profileImageUrl);
        return PeautyApiResponse.ok(response);
    }
}
