package com.peauty.auth.provider;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.OidcProviderType;
import com.peauty.domain.user.SocialInfo;
import com.peauty.auth.client.AppleAuthClient;
import com.peauty.auth.client.OidcPublicKeyList;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.PublicKey;

@Component
@RequiredArgsConstructor
public class AppleOidcProvider implements OidcProvider {

    private final AppleAuthClient appleAuthClient;
    private final PublicKeyProvider publicKeyProvider;
    private final JwtProvider jwtProvider;

    @Override
    public SocialInfo getSocialInfo(String idToken) {
        OidcPublicKeyList oidcPublicKeyList = appleAuthClient.getPublicKeys();
        PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), oidcPublicKeyList);
        Claims claims = jwtProvider.parseClaims(idToken, publicKey);
        return new SocialInfo(
                claims.getSubject(),
                OidcProviderType.APPLE,
                claims.get("email", String.class).split("@")[0],
                "https://apple/profile-image-default"
        );
    }

    @Override
    public String getIdToken(String authCode) {
        // TODO Apple getIdToken impl
        throw new PeautyException(PeautyResponseCode.NOT_YET_IMPLEMENTED);
    }
}