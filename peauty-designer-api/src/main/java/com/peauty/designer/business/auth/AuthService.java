package com.peauty.designer.business.auth;


import com.peauty.designer.business.auth.dto.SignInResult;
import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.business.auth.dto.TestSignCommand;
import com.peauty.domain.user.SocialPlatform;

public interface AuthService {
    SignInResult signIn(SocialPlatform socialPlatform, String code);
    SignUpResult signUp(SignUpCommand command);
    SignUpResult signWithIdToken(TestSignCommand command);
}
