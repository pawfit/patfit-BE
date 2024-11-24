package com.peauty.customer.presentation.controller.auth;

import com.peauty.customer.business.auth.AuthService;
import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignInResponse;
import com.peauty.customer.presentation.controller.auth.dto.SignUpRequest;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
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
@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/kakao-sign-in")
    @Operation(summary = "고객 카카오 로그인 리다이렉트", description = "고객의 카카오 로그인 리다이렉트 진입점입니다.")
    public SignInResponse kakaoSignIn(
            @RequestParam final String code,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.KAKAO, code);
        return redirectOrMapToSignInResponse(response, result);
    }

    @GetMapping("/google-sign-in")
    @Operation(summary = "고객 구글 로그인 리다이렉트", description = "고객의 구글 로그인 리다이렉트 진입점입니다.")
    public SignInResponse googleSignIn(
            @RequestParam final String code,
            @RequestParam final String state,
            HttpServletResponse response
    ) throws IOException {
        SignInResult result = authService.signIn(SocialPlatform.GOOGLE, code);
        return redirectOrMapToSignInResponse(response, result);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "고객 회원가입", description = "고객의 회원가입 진입점입니다.")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        SignUpResult result = authService.signUp(request.toCommand());
        return SignUpResponse.from(result);
    }

    private SignInResponse redirectOrMapToSignInResponse(HttpServletResponse response, SignInResult result) throws IOException {
        if (result.accessToken() == null) {
            // TODO redirect path 를 프론트엔드 엔드포인트로
            // TODO redirectUrl 은 서비스 단에 숨기기
            String redirectUrl = UriComponentsBuilder.fromPath("http://localhost:8080/v1/auth/test/sign-in")
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
