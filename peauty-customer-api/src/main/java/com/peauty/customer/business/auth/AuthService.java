package com.peauty.customer.business.auth;

import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.domain.user.SocialPlatform;

public interface AuthService {
    SignInResult signIn(SocialPlatform socialPlatform, String code);
    SignUpResult signUp(SignUpCommand command);
    SignUpResult signWithIdToken(SocialPlatform socialPlatform, String idToken);
}
