package com.peauty.customer.business.auth;

import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.SocialInfo;

public interface AuthPort {
    String getIdTokenByCode(SocialPlatform socialPlatform, String code);
    SocialInfo getSocialInfoFromIdToken(SocialPlatform socialPlatform, String idToken);
    SignTokens generateSignTokens(User user);
}
