package com.peauty.designer.presentation.controller.auth;

import com.peauty.designer.business.auth.AuthService;
import com.peauty.designer.business.auth.dto.SignInResult;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.presentation.controller.auth.dto.SignInResponse;
import com.peauty.designer.presentation.controller.auth.dto.SignUpRequest;
import com.peauty.designer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Auth API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/kakao-sign-in")
    @Operation(summary = "디자이너 카카오 로그인 리다이렉트", description = "디자이너의 카카오 로그인 리다이렉트 진입점입니다.")
    public SignInResponse kakaoSignIn(
            @RequestParam final String code,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.KAKAO, code);
        return redirectOrMapToSignInResponse(response, result);
    }

    @GetMapping("/google-sign-in")
    @Operation(summary = "디자이너 구글 로그인 리다이렉트", description = "디자이너의 구글 로그인 리다이렉트 진입점입니다.")
    public SignInResponse googleSignIn(
            @RequestParam final String code,
            @RequestParam final String state,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.GOOGLE, code);
        return redirectOrMapToSignInResponse(response, result);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "디자이너 회원가입", description = "디자이너의 회원가입 진입점입니다.")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        SignUpResult result = authService.signUp(request.toCommand());
        return SignUpResponse.from(result);
    }

    private SignInResponse redirectOrMapToSignInResponse(HttpServletResponse response, SignInResult result) throws IOException {
        if (result.accessToken() == null) {
            // TODO redirect path 를 프론트엔드 엔드포인트로
            // TODO redirectUrl 은 서비스 단에 숨기기
            String redirectUrl = UriComponentsBuilder.fromPath("http://localhost:8081/v1/auth/test/sign-in")
                    .queryParam("socialId", result.socialId())
                    .queryParam("socialPlatform", result.socialPlatform())
                    .queryParam("nickname", result.nickname())
                    .queryParam("profileImageUrl", result.profileImageUrl())
                    .build()
                    .encode()
                    .toUriString();
            response.sendRedirect(redirectUrl);
            return null;
        }
        return SignInResponse.from(result);
    }
}
