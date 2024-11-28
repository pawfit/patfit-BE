package com.peauty.designer.business.auth;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialInfo;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.AuthInfo;

public interface AuthPort {
    String getIdTokenByCode(SocialPlatform socialPlatform, String code);
    SocialInfo getSocialInfoFromIdToken(SocialPlatform socialPlatform, String idToken);
    SignTokens generateSignTokens(AuthInfo authInfo);
}
