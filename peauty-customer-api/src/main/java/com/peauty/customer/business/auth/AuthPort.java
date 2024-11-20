package com.peauty.customer.business.auth;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;
import com.peauty.domain.user.OidcProviderType;
import com.peauty.domain.user.SocialInfo;

public interface AuthPort {
    String getIdTokenByCode(OidcProviderType oidcProviderType, String code);
    SocialInfo getSocialInfoFromIdToken(OidcProviderType oidcProviderType, String idToken);
    SignTokens generateSignTokens(User user);
}
