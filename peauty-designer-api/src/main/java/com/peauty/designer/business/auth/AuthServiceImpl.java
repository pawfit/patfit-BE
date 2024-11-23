package com.peauty.designer.business.auth;

import com.peauty.designer.business.auth.dto.SignInResult;
import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.auth.dto.SignUpResult;
import com.peauty.designer.business.designer.DesignerPort;
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
        designerPort.checkCustomerPhoneNumDuplicated(command.phoneNum());
        designerPort.checkCustomerNicknameDuplicated(command.nickname());
        User registeredDesigner = designerPort.registerNewDesigner(command);
        SignTokens signTokens = authPort.generateSignTokens(registeredDesigner);
        return SignUpResult.from(signTokens, registeredDesigner);
    }

    @Override
    @Transactional
    public SignUpResult signWithIdToken(SocialPlatform socialPlatform, String idToken, String nickname, String phoneNum) {
        SocialInfo socialInfo = authPort.getSocialInfoFromIdToken(socialPlatform, idToken);
        return designerPort.findBySocialId(socialInfo.socialId())
                .map(user -> {
                    SignTokens signTokens = authPort.generateSignTokens(user);
                    return SignUpResult.from(signTokens, user);
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

    private SignInResult createRegisteredSignInResult(User user) {
        SignTokens signTokens = authPort.generateSignTokens(user);
        return SignInResult.from(signTokens, user);
    }
}
