
package customer;

import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.customer.business.customer.CustomerServiceImpl;
import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerServiceImpl 테스트")
class CustomerServiceImplTest {

    @Mock
    private CustomerPort customerPort;

    @Mock
    private InternalPort internalPort;

    @Mock
    private DesignerPort designerPort;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("1.1 고객 프로필 이미지 업로드 성공 및 실패 테스트")
    void customerProfileImageUploadTest() {
        // Arrange
        Long customerId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);
        String uploadedImageUrl = "http://example.com/image.jpg";

        // 성공 케이스 설정
        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(internalPort.uploadImage(mockFile)).thenReturn(uploadedImageUrl);
        when(mockCustomer.getCustomerId()).thenReturn(customerId);
        when(mockCustomer.getProfileImageUrl()).thenReturn(uploadedImageUrl);
        when(customerPort.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act - 성공 케이스
        UploadProfileImageResult successResult = customerService.uploadProfileImage(customerId, mockFile);

        // Assert - 성공 케이스
        assertNotNull(successResult);
        assertEquals(customerId, successResult.customerId());
        assertEquals(uploadedImageUrl, successResult.uploadedProfileImageUrl());
        verify(customerPort).save(mockCustomer);
        verify(mockCustomer).updateProfileImageUrl(uploadedImageUrl);

        // 실패 케이스 설정
        when(customerPort.getByCustomerIdWithoutPuppies(customerId))
                .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        // Act & Assert - 실패 케이스
        assertThatThrownBy(() -> customerService.uploadProfileImage(customerId, mockFile))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_USER)
                .hasMessage(PeautyResponseCode.NOT_EXIST_USER.getMessage());
    }

    @Test
    @DisplayName("1.2 고객 프로필 정보 조회 성공 및 실패 테스트")
    void getCustomerProfileTest() {
        // Arrange
        Long customerId = 1L;

        // 성공 케이스 설정
        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(mockCustomer.getCustomerId()).thenReturn(customerId);
        when(mockCustomer.getName()).thenReturn("신재혁");
        when(mockCustomer.getNickname()).thenReturn("ㅅㅈㅎ");
        when(mockCustomer.getProfileImageUrl()).thenReturn("http://example.com/profile.jpg");
        when(mockCustomer.getAddress()).thenReturn("서울특별시 송파구");
        when(mockCustomer.getPhoneNumber()).thenReturn("010-1111-2222");

        // Act - 성공 케이스
        GetCustomerProfileResult successResult = customerService.getCustomerProfile(customerId);

        // Assert - 성공 케이스
        assertNotNull(successResult);
        assertEquals(customerId, successResult.customerId());
        assertEquals("신재혁", successResult.name());
        assertEquals("ㅅㅈㅎ", successResult.nickname());
        assertEquals("http://example.com/profile.jpg", successResult.profileImageUrl());
        assertEquals("서울특별시 송파구", successResult.address());
        assertEquals("010-1111-2222", successResult.phoneNumber());
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);

        // 실패 케이스 설정
        when(customerPort.getByCustomerIdWithoutPuppies(customerId))
                .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        // Act & Assert - 실패 케이스
        assertThatThrownBy(() -> customerService.getCustomerProfile(customerId))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_USER)
                .hasMessage(PeautyResponseCode.NOT_EXIST_USER.getMessage());
    }

    @Test
    @DisplayName("1.3 고객 닉네임 중복 체크 성공 및 실패 테스트")
    void checkCustomerNicknameDuplicatedTest() {
        // Arrange
        String nickname = "유레카";

        // 성공 케이스
        doNothing().when(customerPort).checkCustomerNicknameDuplicated(nickname);

        // Act & Assert - 성공 케이스
        assertDoesNotThrow(() -> customerService.checkCustomerNicknameDuplicated(nickname));
        verify(customerPort).checkCustomerNicknameDuplicated(nickname);

        // 실패 케이스 설정
        doThrow(new PeautyException(PeautyResponseCode.ALREADY_EXIST_NICKNAME))
                .when(customerPort).checkCustomerNicknameDuplicated(nickname);

        // Act & Assert - 실패 케이스
        assertThatThrownBy(() -> customerService.checkCustomerNicknameDuplicated(nickname))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_EXIST_NICKNAME)
                .hasMessage(PeautyResponseCode.ALREADY_EXIST_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("1.4 고객 프로필 업데이트 성공 및 실패 테스트")
    void updateCustomerProfileTest() {
        // Arrange
        Long customerId = 1L;
        UpdateCustomerProfileCommand validCommand = new UpdateCustomerProfileCommand(
                "양석진",
                "Yang",
                "010-2222-3333",
                "경기도 분당",
                "http://example.com/updated.jpg"
        );

        UpdateCustomerProfileCommand invalidCommand = new UpdateCustomerProfileCommand(
                null, // 이름 누락
                "Yang",
                "010-2222-3333",
                "경기도 분당",
                "http://example.com/updated.jpg"
        );

        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(customerPort.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act - 성공 케이스
        UpdateCustomerProfileResult successResult = customerService.updateCustomerProfile(customerId, validCommand);

        // Assert - 성공 케이스
        assertNotNull(successResult);
        verify(mockCustomer).updateName("양석진");
        verify(mockCustomer).updateNickname("Yang");
        verify(mockCustomer).updatePhoneNumber("010-2222-3333");
        verify(mockCustomer).updateAddress("경기도 분당");
        verify(mockCustomer).updateProfileImageUrl("http://example.com/updated.jpg");
        verify(customerPort).save(mockCustomer);

        // 실패 케이스 설정
        doThrow(new PeautyException(PeautyResponseCode.WRONG_PARAMETER))
                .when(mockCustomer).updateName(null);

        // Act & Assert - 실패 케이스
        assertThatThrownBy(() -> customerService.updateCustomerProfile(customerId, invalidCommand))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.WRONG_PARAMETER)
                .hasMessage(PeautyResponseCode.WRONG_PARAMETER.getMessage());
    }

    @Test
    @DisplayName("1.5 디자이너 뱃지 조회 성공 및 실패 테스트")
    void getDesignerBadgesByCustomerTest() {
        // Arrange
        Long designerId = 1L;

        Badge mockBadge1 = mock(Badge.class);
        Badge mockBadge2 = mock(Badge.class);
        when(mockBadge1.getIsRepresentativeBadge()).thenReturn(true);
        when(mockBadge2.getIsRepresentativeBadge()).thenReturn(false);
        when(designerPort.getAcquiredBadges(designerId)).thenReturn(List.of(mockBadge1, mockBadge2));

        // Act - 성공 케이스
        GetDesignerBadgesForCustomerResult successResult = customerService.getDesignerBadgesByCustomer(designerId);

        // Assert - 성공 케이스
        assertNotNull(successResult);
        assertEquals(2, successResult.acquiredBadges().size());
        assertEquals(1, successResult.representativeBadges().size());
        verify(designerPort).getAcquiredBadges(designerId);

        // 실패 케이스 설정
        when(designerPort.getAcquiredBadges(designerId))
                .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));

        // Act & Assert - 실패 케이스
        assertThatThrownBy(() -> customerService.getDesignerBadgesByCustomer(designerId))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_DESIGNER)
                .hasMessage(PeautyResponseCode.NOT_EXIST_DESIGNER.getMessage());
    }
}
