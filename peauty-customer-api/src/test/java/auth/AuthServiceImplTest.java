package auth;

import com.peauty.customer.business.auth.AuthPort;
import com.peauty.customer.business.auth.AuthServiceImpl;
import com.peauty.customer.business.auth.dto.SignInResult;
import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.auth.dto.SignUpResult;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.SocialInfo;
import com.peauty.domain.user.SocialPlatform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl 테스트")
class AuthServiceImplTest {

    @Mock
    private CustomerPort customerPort;

    @Mock
    private AuthPort authPort;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("1.1 사용자 signIn 성공 테스트")
    void signIn_ShouldReturnSignInResult_WhenUserExists() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.KAKAO;
        String code = "123123456654";
        String idToken = "987789987789";

        SocialInfo socialInfo = new SocialInfo("321098321098", socialPlatform, "Shin", "image-url");
        Customer customer = mock(Customer.class);
        SignTokens signTokens = new SignTokens("464746484746", "363738373637383");

        when(authPort.getIdTokenByCode(socialPlatform, code)).thenReturn(idToken);
        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.of(customer));
        when(authPort.generateSignTokens(customer.getAuthInfo())).thenReturn(signTokens);

        // Act
        SignInResult result = authService.signIn(socialPlatform, code);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("464746484746");
        assertThat(result.refreshToken()).isEqualTo("363738373637383");
        verify(customerPort).findBySocialId(socialInfo.socialId());
    }

    @Test
    @DisplayName("2.1 중복된 소셜 ID로 인한 signUp 실패 테스트")
    void signUp_ShouldThrowException_WhenSocialIdExists() {
        // Arrange
        SignUpCommand command = new SignUpCommand("social-id", SocialPlatform.KAKAO, "name", "010-1234-5678", "address", "nickname", "image-url");

        doThrow(new PeautyException(PeautyResponseCode.ALREADY_EXIST_USER))
                .when(customerPort).checkCustomerSocialIdDuplicated(command.socialId());

        // Act & Assert
        assertThatThrownBy(() -> authService.signUp(command))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_EXIST_USER)
                .hasMessage(PeautyResponseCode.ALREADY_EXIST_USER.getMessage());
    }

   /*
   테스트 방식이 다릅니다.
   일반적인 방식으로는 오류를 띄우는 SignUpCommand 객체를 Mockito가 동일하게 비교할 수 없다고 합니다.
   * 오류 : Strict Stubbing 정책 문제 발생.
   SignupCommand는 객체로 비교되기 때문에,
   equals 메서드가 호출되지 않으면, 같은 값을 가진 다른 SignUpCommand 객체는 다르다고 간주가 됩니다.
   하단 방법으로 argThat을 이용한다고 합니다. -> 최선의 1안
   Test 상단에 @MockitoSettings(strictness = Strictness.LENIENT) // Strict Stubbing 비활성화를 하여 테스트 조건을 완화합니다. -> 좋지않은 2안
   @Test
    @DisplayName("3.1 성공적으로 signWithIdToken으로 사용자 등록")
    void signWithIdToken_ShouldSignUpNewUser_WhenUserDoesNotExist() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.KAKAO;
        String idToken = "id-token";
        String nickname = "new-nickname";
        String phoneNumber = "010-1234-5678";

        SocialInfo socialInfo = new SocialInfo("social-id", socialPlatform, "social-nickname", "image-url");
        SignTokens signTokens = new SignTokens("access-token", "refresh-token");
        Customer customer = mock(Customer.class);
        SignUpCommand command = new SignUpCommand("social-id", socialPlatform, "name", phoneNumber, "address", nickname, "image-url");

        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.empty());
        when(customerPort.registerNewCustomer(command)).thenReturn(customer);
        when(authPort.generateSignTokens(customer.getAuthInfo())).thenReturn(signTokens);

        // Act
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNumber);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("access-token");
        assertThat(result.refreshToken()).isEqualTo("refresh-token");
        verify(customerPort).registerNewCustomer(command);
    }*/

    @Test
    @DisplayName("3.1 signWithIdToken으로 사용자 등록")
    void signWithIdToken_ShouldSignUpNewUser_WhenUserDoesNotExist() {
        // Arrange
        SocialPlatform socialPlatform = SocialPlatform.KAKAO;
        String idToken = "id-token";
        String nickname = "new-nickname";
        String phoneNumber = "010-1234-5678";

        SocialInfo socialInfo = new SocialInfo("social-id", socialPlatform, "social-nickname", "image-url");
        SignTokens signTokens = new SignTokens("access-token", "refresh-token");
        Customer customer = mock(Customer.class);

        when(authPort.getSocialInfoFromIdToken(socialPlatform, idToken)).thenReturn(socialInfo);
        when(customerPort.findBySocialId(socialInfo.socialId())).thenReturn(Optional.empty());
        when(customerPort.registerNewCustomer(argThat(command ->
                command.socialId().equals("social-id") &&
                        command.socialPlatform() == SocialPlatform.KAKAO &&
                        command.name().equals("social-nickname") &&
                        command.nickname().equals("new-nickname") &&
                        command.phoneNumber().equals("010-1234-5678") &&
                        command.address().equals("test_address") &&
                        command.profileImageUrl().equals("image-url")
        ))).thenReturn(customer);

        when(authPort.generateSignTokens(customer.getAuthInfo())).thenReturn(signTokens);

        // Act
        SignUpResult result = authService.signWithIdToken(socialPlatform, idToken, nickname, phoneNumber);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("access-token");
        assertThat(result.refreshToken()).isEqualTo("refresh-token");

        verify(customerPort).registerNewCustomer(any(SignUpCommand.class));
    }

}
