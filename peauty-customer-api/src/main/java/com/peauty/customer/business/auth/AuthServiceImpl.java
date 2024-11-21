package com.peauty.customer.business.auth;

import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialInfo;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.User;
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
    public SignInResult signIn(SocialPlatform socialPlatform, String code) {
        String idToken = authPort.getIdTokenByCode(socialPlatform, code);
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(socialPlatform, idToken);
        return customerPort.findBySocialId(socialInfo.socialId())
                .map(this::createRegisteredSignInResult)
                .orElse(createUnregisteredSignInResult(socialInfo));
    }

    @Override
    @Transactional
    public SignUpResult signUp(SignUpCommand command) {
        customerPort.checkCustomerSocialIdDuplicated(command.socialId());
        customerPort.checkCustomerPhoneNumDuplicated(command.phoneNum());
        customerPort.checkCustomerNicknameDuplicated(command.nickname());
        User registeredCustomer = customerPort.registerNewCustomer(command);
        SignTokens signTokens = authPort.generateSignTokens(registeredCustomer);
        return SignUpResult.from(signTokens, registeredCustomer);
    }

    @Override
    public SignUpResult signWithIdToken(SocialPlatform socialPlatform, String idToken, String nickname, String phoneNum) {
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(socialPlatform, idToken);
        return customerPort.findBySocialId(socialInfo.socialId())
                .map(user -> {
                    SignTokens signTokens = authPort.generateSignTokens(user);
                    return new SignUpResult(
                            user.getUserId(),
                            signTokens.accessToken(),
                            signTokens.refreshToken()
                    );
                })
                .orElseGet(() -> {
                    SignUpCommand signUpCommand = new SignUpCommand(
                            socialInfo.socialId(),
                            socialInfo.socialPlatform(),
                            socialInfo.nickname(),
                            phoneNum,
                            "test_address",
                            nickname,
                            socialInfo.pictureImageUrl()
                    );
                    customerPort.checkCustomerPhoneNumDuplicated(phoneNum);
                    customerPort.checkCustomerNicknameDuplicated(nickname);
                    User registeredCustomer = customerPort.registerNewCustomer(signUpCommand);
                    SignTokens signTokens = authPort.generateSignTokens(registeredCustomer);
                    return SignUpResult.from(signTokens, registeredCustomer);
                });
    }

    private SignInResult createUnregisteredSignInResult(SocialInfo socialInfo) {
        return new SignInResult(
                null,
                null,
                socialInfo.socialId(),
                socialInfo.socialPlatform(),
                socialInfo.nickname(),
                socialInfo.pictureImageUrl()
        );
    }

    private SignInResult createRegisteredSignInResult(User user) {
        SignTokens signTokens = authPort.generateSignTokens(user);
        return SignInResult.from(signTokens, user);
    }
}
