package com.peauty.customer.presentation.controller.auth;

import com.peauty.customer.business.auth.AuthService;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.SocialPlatform;
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
    public SignUpResponse signTester(
            @RequestParam SocialPlatform socialPlatform,
            @RequestParam String idToken,
            @RequestParam String nickname,
            @RequestParam String phoneNum
    ) {
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNum);
        return SignUpResponse.from(result);
    }

    @GetMapping("/sign-in")
    public void testSignIn(
            @RequestParam("socialId") String socialId,
            @RequestParam("socialPlatform") SocialPlatform socialPlatform,
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
