package com.peauty.designer.presentation.controller.auth;


import com.peauty.designer.business.auth.AuthService;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.designer.presentation.controller.auth.dto.TestSignRequest;
import com.peauty.domain.response.PeautyApiResponse;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Auth Test", description = "Auth Test API")
@RequestMapping("/v1/auth/test")
@RequiredArgsConstructor
public class AuthTestController {

    private final AuthService authService;

    @PostMapping("/sign")
    @Operation(summary = "디자이너 테스터 로그인&회원가입", description = "디자이너 테스터의 로그인&회원가입 통합 진입점입니다.")
    public SignUpResponse signTester(@RequestBody TestSignRequest request) {
        SignUpResult result = authService.signWithIdToken(request.toCommand());
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
