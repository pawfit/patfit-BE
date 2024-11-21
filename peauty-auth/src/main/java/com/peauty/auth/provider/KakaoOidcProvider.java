package com.peauty.auth.provider;

import com.peauty.domain.user.OidcProviderType;
import com.peauty.domain.user.SocialInfo;
import com.peauty.auth.client.KakaoAuthClient;
import com.peauty.auth.client.OidcPublicKeyList;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
@RequiredArgsConstructor
public class KakaoOidcProvider implements OidcProvider {

    private final KakaoAuthClient kakaoAuthClient;
    private final PublicKeyProvider publicKeyProvider;
    private final JwtProvider jwtProvider;

    @Override
    public SocialInfo getSocialInfo(String idToken) {
        OidcPublicKeyList oidcPublicKeyList = kakaoAuthClient.getPublicKeys();
        PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), oidcPublicKeyList);
        Claims claims = jwtProvider.parseClaims(idToken, publicKey);
        return new SocialInfo(
                claims.getSubject(),
                OidcProviderType.KAKAO,
                claims.get("nickname", String.class),
                claims.get("picture", String.class)
        );
    }

    @Override
    public String getIdToken(String authCode) {
        return kakaoAuthClient.getIdTokenFromKakao(authCode);
    }
}