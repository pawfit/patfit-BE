/*
package auth;

import com.peauty.customer.business.auth.AuthPort;
import com.peauty.customer.business.auth.AuthServiceImpl;
import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialInfo;
import com.peauty.domain.user.SocialPlatform;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl 테스트")
public class AuthServiceImplTest {

    @Mock
    private CustomerPort customerPort;

    @Mock
    private AuthPort authPort;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("이미 등록된 사용자가 소셜 로그인 시 성공적인 SignInResult를 반환한다")
    public void signIn_ShouldReturnRegisteredSignInResult() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.GOOGLE;
        String code = "test_code";
        String idToken = "test_id_token";
        SocialInfo socialInfo = new SocialInfo("socialId123", socialPlatform, "nickname", "test_image_url");
        Customer mockCustomer = mock(Customer.class);

        when(authPort.getIdTokenByCode(socialPlatform, code)).thenReturn(idToken);
        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.of(mockCustomer));
        when(authPort.generateSignTokens(mockCustomer.getAuthInfo())).thenReturn(new SignTokens("accessToken", "refreshToken"));

        // Act
        SignInResult result = authService.signIn(socialPlatform, code);

        // Assert
        assertNotNull(result);
        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());
        verify(customerPort).findBySocialId(socialInfo.socialId());
    }

    @Test
    @DisplayName("등록되지 않은 사용자가 소셜 로그인 시 Unregistered SignInResult를 반환한다")
    public void signIn_ShouldReturnUnregisteredSignInResult() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.GOOGLE;
        String code = "test_code";
        String idToken = "test_id_token";
        SocialInfo socialInfo = new SocialInfo("socialId123", socialPlatform, "nickname", "test_image_url");

        when(authPort.getIdTokenByCode(socialPlatform, code)).thenReturn(idToken);
        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.empty());

        // Act
        SignInResult result = authService.signIn(socialPlatform, code);

        // Assert
        assertNotNull(result);
        assertNull(result.accessToken());
        assertNull(result.refreshToken());
        assertEquals("socialId123", result.socialId());
        verify(customerPort).findBySocialId(socialInfo.socialId());
    }

    @Test
    @DisplayName("회원 가입 요청 시 중복 체크 후 고객이 등록된다")
    public void signUp_ShouldRegisterCustomer() {
        // Arrange
        SignUpCommand command = new SignUpCommand(
                "socialId123",
                SocialPlatform.GOOGLE,
                "nickname",
                "1234567890",
                "test_address",
                "test_nickname",
                "test_image_url"
        );
        Customer mockCustomer = mock(Customer.class);
        SignTokens signTokens = new SignTokens("accessToken", "refreshToken");

        doNothing().when(customerPort).checkCustomerSocialIdDuplicated(command.socialId());
        doNothing().when(customerPort).checkCustomerPhoneNumberDuplicated(command.phoneNumber());
        doNothing().when(customerPort).checkCustomerNicknameDuplicated(command.nickname());
        when(customerPort.registerNewCustomer(command)).thenReturn(mockCustomer);
        when(authPort.generateSignTokens(mockCustomer.getAuthInfo())).thenReturn(signTokens);

        // Act
        SignUpResult result = authService.signUp(command);

        // Assert
        assertNotNull(result);
        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());
        verify(customerPort).registerNewCustomer(command);
    }

    @Test
    @DisplayName("ID 토큰을 이용한 회원 가입 또는 로그인이 수행된다")
    public void signWithIdToken_ShouldSignUpOrSignInCustomer() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.GOOGLE;
        String idToken = "test_id_token";
        String nickname = "test_nickname";
        String phoneNum = "1234567890";
        SocialInfo socialInfo = new SocialInfo("socialId123", socialPlatform, "nickname", "test_image_url");
        Customer mockCustomer = mock(Customer.class);
        SignTokens signTokens = new SignTokens("accessToken", "refreshToken");

        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.of(mockCustomer));
        when(authPort.generateSignTokens(mockCustomer.getAuthInfo())).thenReturn(signTokens);

        // Act
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNum);

        // Assert
        assertNotNull(result);
        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());
        verify(authPort).getSocialInfoFromIdToken(socialPlatform, idToken);
        verify(customerPort).findBySocialId(socialInfo.socialId());
    }
}*/
