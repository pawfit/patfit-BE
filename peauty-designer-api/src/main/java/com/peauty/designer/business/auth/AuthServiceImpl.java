package com.peauty.designer.business.auth;

import com.peauty.designer.business.auth.dto.SignInResult;
import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.business.auth.dto.TestSignCommand;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.domain.designer.Designer;
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

    private final DesignerPort designerPort;
    private final AuthPort authPort;

    @Override
    public SignInResult signIn(SocialPlatform socialPlatform, String code) {
        String idToken = authPort.getIdTokenByCode(socialPlatform, code);
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(socialPlatform, idToken);
        return designerPort.findBySocialId(socialInfo.socialId())
                .map(this::createRegisteredSignInResult)
                .orElse(createUnregisteredSignInResult(socialInfo));
    }

    @Override
    @Transactional
    public SignUpResult signUp(SignUpCommand command) {
        designerPort.checkCustomerPhoneNumDuplicated(command.phoneNumber());
        designerPort.checkCustomerNicknameDuplicated(command.nickname());
        Designer registeredCustomer = designerPort.registerNewDesigner(command);
        SignTokens signTokens = authPort.generateSignTokens(registeredCustomer.getAuthInfo());
        return SignUpResult.from(signTokens, registeredCustomer);
    }

    @Override
    @Transactional
    public SignUpResult signWithIdToken(TestSignCommand command) {
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(command.socialPlatform(), command.idToken());
        return designerPort.findBySocialId(socialInfo.socialId())
                .map(designer -> {
                    SignTokens signTokens = authPort.generateSignTokens(designer.getAuthInfo());
                    return SignUpResult.from(signTokens, designer);
                })
                .orElseGet(() -> {
                    SignUpCommand signUpCommand = new SignUpCommand(
                            socialInfo.socialId(),
                            socialInfo.socialPlatform(),
                            socialInfo.nickname(),
                            command.phoneNumber(),
                            "test@test.com",
                            command.nickname(),
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

    private SignInResult createRegisteredSignInResult(Designer designer) {
        SignTokens signTokens = authPort.generateSignTokens(designer.getAuthInfo());
        return SignInResult.from(signTokens, designer);
    }
}
