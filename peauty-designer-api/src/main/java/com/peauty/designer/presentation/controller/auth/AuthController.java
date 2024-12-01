package com.peauty.designer.presentation.controller.auth;

import com.peauty.designer.business.auth.AuthService;
import com.peauty.designer.business.auth.dto.SignInResult;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.presentation.controller.auth.dto.SignUpRequest;
import com.peauty.designer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.SocialPlatform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${oauth.frontend-redirect-url}")
    private String frontendRedirectUrl;
    private final AuthService authService;

    @GetMapping("/kakao-sign-in")
    @Operation(summary = " 카카오 로그인 리다이렉트", description = "디자이너의 카카오 로그인 리다이렉트 진입점입니다.")
    public void kakaoSignIn(
            @RequestParam final String code,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.KAKAO, code);
        redirectBySignInResult(response, result);
    }

    @GetMapping("/google-sign-in")
    @Operation(summary = "디자이너 구글 로그인 리다이렉트", description = "디자이너의 구글 로그인 리다이렉트 진입점입니다.")
    public void googleSignIn(
            @RequestParam final String code,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.GOOGLE, code);
        redirectBySignInResult(response, result);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "디자이너 회원가입", description = "디자이너의 회원가입 진입점입니다.")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        SignUpResult result = authService.signUp(request.toCommand());
        return SignUpResponse.from(result);
    }

    private void redirectBySignInResult(HttpServletResponse response, SignInResult result) throws IOException {
        if (result.accessToken() == null) {
            String redirectUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
                    .path("/signup")
                    .queryParam("socialId", result.socialId())
                    .queryParam("socialPlatform", result.socialPlatform())
                    .queryParam("workspaceName", result.nickname())
                    .queryParam("profileImageUrl", result.profileImageUrl())
                    .build()
                    .encode()
                    .toUriString();
            response.sendRedirect(redirectUrl);
            return;
        }
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
                .path("/signin")
                .queryParam("accessToken", result.accessToken())
                .queryParam("refreshToken", result.refreshToken())
                .build()
                .encode()
                .toUriString();
        response.sendRedirect(redirectUrl);
    }
}
