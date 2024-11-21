package com.peauty.customer.presentation.controller.auth;

import com.peauty.customer.business.auth.AuthService;
import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignInResponse;
import com.peauty.customer.presentation.controller.auth.dto.SignUpRequest;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.OidcProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/auth/test")
@RequiredArgsConstructor
public class AuthTestController {

    private final AuthService authService;

    @PostMapping("/sign")
    public SignUpResponse sign(
            @RequestParam OidcProviderType socialPlatform,
            @RequestParam String idToken
    ) {
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken);
        return SignUpResponse.from(result);
    }

    @GetMapping("/sign-in")
    public void testSignIn(
            @RequestParam("socialId") String socialId,
            @RequestParam("socialPlatform") OidcProviderType socialPlatform,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImageUrl") String profileImageUrl
    ) {
        log.info("""
                        Redirect Parameters:
                        socialId: {}
                        socialPlatform: {}
                        nickname: {}
                        profileImageUrl: {}
                        """,
                socialId, socialPlatform, nickname, profileImageUrl
        );
    }
}
