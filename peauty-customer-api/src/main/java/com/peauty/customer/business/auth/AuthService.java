package com.peauty.customer.business.auth;

import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.presentation.controller.auth.dto.SignUpResponse;
import com.peauty.domain.user.OidcProviderType;

public interface AuthService {
    SignInResult signIn(OidcProviderType oidcProviderType, String code);
    SignUpResult signUp(SignUpCommand command);
    SignUpResult signWithIdToken(OidcProviderType oidcProviderType, String idToken);
}
