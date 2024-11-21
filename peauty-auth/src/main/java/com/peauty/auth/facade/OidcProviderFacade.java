package com.peauty.auth.facade;

import com.peauty.domain.user.OidcProviderType;
import com.peauty.domain.user.SocialInfo;
import com.peauty.auth.provider.AppleOidcProvider;
import com.peauty.auth.provider.GoogleOidcProvider;
import com.peauty.auth.provider.KakaoOidcProvider;
import com.peauty.auth.provider.OidcProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OidcProviderFacade {

    private final AppleOidcProvider appleOidcProvider;
    private final KakaoOidcProvider kakaoOidcProvider;
    private final GoogleOidcProvider googleOidcProvider;

    public SocialInfo getSocialInfo(OidcProviderType provider, String idToken) {
        return getProvider(provider).getSocialInfo(idToken);
    }

    public String getIdToken(OidcProviderType provider, String authCode) {
        return getProvider(provider).getIdToken(authCode);
    }

    private OidcProvider getProvider(OidcProviderType provider) {
        return switch (provider) {
            case APPLE -> appleOidcProvider;
            case KAKAO -> kakaoOidcProvider;
            case GOOGLE -> googleOidcProvider;
        };
    }
}