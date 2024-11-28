package com.peauty.customer.business.auth;

import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialInfo;
import com.peauty.domain.user.SocialPlatform;
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
        customerPort.checkCustomerPhoneNumDuplicated(command.phoneNumber());
        customerPort.checkCustomerNicknameDuplicated(command.nickname());
        Customer registeredCustomer = customerPort.registerNewCustomer(command);
        SignTokens signTokens = authPort.generateSignTokens(registeredCustomer.getAuthInfo());
        return SignUpResult.from(signTokens, registeredCustomer);
    }

    @Override
    @Transactional
    public SignUpResult signWithIdToken(SocialPlatform socialPlatform, String idToken, String nickname, String phoneNum) {
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(socialPlatform, idToken);
        return customerPort.findBySocialId(socialInfo.socialId())
                .map(customer -> {
                    SignTokens signTokens = authPort.generateSignTokens(customer.getAuthInfo());
                    return SignUpResult.from(signTokens, customer);
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
                    return signUp(signUpCommand);
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

    private SignInResult createRegisteredSignInResult(Customer customer) {
        SignTokens signTokens = authPort.generateSignTokens(customer.getAuthInfo());
        return SignInResult.from(signTokens, customer);
    }
}
