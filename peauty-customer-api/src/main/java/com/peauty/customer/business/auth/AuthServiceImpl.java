package com.peauty.customer.business.auth;

import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.User;
import com.peauty.domain.user.OidcProviderType;
import com.peauty.domain.user.SocialInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final CustomerPort customerPort;
    private final AuthPort authPort;

    @Override
    public SignInResult signIn(OidcProviderType oidcProviderType, String code) {
        String idToken = authPort.getIdTokenByCode(oidcProviderType, code);
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(oidcProviderType, idToken);
        return customerPort.findByOidcProviderId(socialInfo.oidcProviderId())
                .map(this::createRegisteredSignInResult)
                .orElse(createUnregisteredSignInResult(socialInfo));
    }

    @Override
    @Transactional
    public SignUpResult signUp(SignUpCommand command) {
        customerPort.checkCustomerPhoneNumDuplicated(command.phoneNum());
        customerPort.checkCustomerNicknameDuplicated(command.nickname());
        User registeredCustomer = customerPort.registerNewCustomer(command);
        SignTokens signTokens = authPort.generateSignTokens(registeredCustomer);
        return SignUpResult.from(signTokens, registeredCustomer);
    }

    @Override
    public SignUpResult signWithIdToken(OidcProviderType oidcProviderType, String idToken) {
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(oidcProviderType, idToken);
        return customerPort.findByOidcProviderId(socialInfo.oidcProviderId())
                .map(user -> {
                    SignTokens signTokens = authPort.generateSignTokens(user);
                    return new SignUpResult(
                            user.id(),
                            signTokens.accessToken(),
                            signTokens.refreshToken()
                    );
                })
                .orElseGet(() -> {
                    SignUpCommand signUpCommand = new SignUpCommand(
                            socialInfo.oidcProviderId(),
                            socialInfo.oidcProviderType(),
                            socialInfo.nickname(),
                            "010-0000-0000",
                            "서울특별시 강남구 선릉로 428 2층",
                            socialInfo.nickname(),
                            socialInfo.pictureImageUrl()
                    );
                    return signUp(signUpCommand);
                });
    }

    private SignInResult createUnregisteredSignInResult(SocialInfo socialInfo) {
        return new SignInResult(
                null,
                null,
                socialInfo.oidcProviderId(),
                socialInfo.oidcProviderType(),
                socialInfo.nickname(),
                socialInfo.pictureImageUrl()
        );
    }

    private SignInResult createRegisteredSignInResult(User user) {
        SignTokens signTokens = authPort.generateSignTokens(user);
        return SignInResult.from(signTokens, user);
    }
}