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
// ExtendWith(MockitoExtension.class)와 충돌하기 때문에 두 방식을 동시에 사용하면 안 된다.
 /*   @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }*/

/*
============================================================
========================Success Case========================
============================================================
    */
    @Test
    @DisplayName("1.1 고객 프로필 이미지 업로드 테스트")
    void customerProfileImageUploadSuccessCase() {
        // Arrange
        Long customerId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);
        String uploadedImageUrl = "http://example.com/image.jpg";

        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(internalPort.uploadImage(mockFile)).thenReturn(uploadedImageUrl);

        when(mockCustomer.getCustomerId()).thenReturn(customerId);
        when(mockCustomer.getProfileImageUrl()).thenReturn(uploadedImageUrl);
        when(customerPort.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act
        UploadProfileImageResult result = customerService.uploadProfileImage(customerId, mockFile);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertEquals(uploadedImageUrl, result.uploadedProfileImageUrl());
        verify(customerPort).save(mockCustomer);
        verify(mockCustomer).updateProfileImageUrl(uploadedImageUrl);
    }

    @Test
    @DisplayName("1.2 고객 프로필 정보 조회 성공 테스트")
    void getCustomerProfileSuccessCase() {
        // Arrange
        Long customerId = 1L;
        Customer mockCustomer = mock(Customer.class);

        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);

        when(mockCustomer.getCustomerId()).thenReturn(customerId);
        when(mockCustomer.getName()).thenReturn("신재혁");
        when(mockCustomer.getNickname()).thenReturn("ㅅㅈㅎ");
        when(mockCustomer.getProfileImageUrl()).thenReturn("http://example.com/profile.jpg");
        when(mockCustomer.getAddress()).thenReturn("서울특별시 송파구");
        when(mockCustomer.getPhoneNumber()).thenReturn("010-1111-2222");

        // Act
        GetCustomerProfileResult result = customerService.getCustomerProfile(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertEquals("신재혁", result.name());
        assertEquals("ㅅㅈㅎ", result.nickname());
        assertEquals("http://example.com/profile.jpg", result.profileImageUrl());
        assertEquals("서울특별시 송파구", result.address());
        assertEquals("010-1111-2222", result.phoneNumber());
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);
    }

    @Test
    @DisplayName("1.3 고객 프로필 업데이트 성공 테스트")
    void updateCustomerProfileSuccessCase() {
        // Arrange
        Long customerId = 1L;
        UpdateCustomerProfileCommand command = new UpdateCustomerProfileCommand(
                "양석진",
                "Yang",
                "010-2222-3333",
                "경기도 분당",
                "http://example.com/updated.jpg"
        );

        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(customerPort.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act
        UpdateCustomerProfileResult result = customerService.updateCustomerProfile(customerId, command);

        // Assert
        assertNotNull(result);
        verify(mockCustomer).updateName("양석진");
        verify(mockCustomer).updateNickname("Yang");
        verify(mockCustomer).updatePhoneNumber("010-2222-3333");
        verify(mockCustomer).updateAddress("경기도 분당");
        verify(mockCustomer).updateProfileImageUrl("http://example.com/updated.jpg");
        verify(customerPort).save(mockCustomer);
    }

    @Test
    @DisplayName("1.4 고객 닉네임 중복 체크 성공 테스트")
    void checkCustomerNicknameDuplicatedSuccessCase() {
        // Arrange
        String nickname = "위진영";

        // Act
        customerService.checkCustomerNicknameDuplicated(nickname);

        // Assert
        verify(customerPort).checkCustomerNicknameDuplicated(nickname);
    }

    @Test
    @DisplayName("1.5 디자이너 뱃지 조회 성공 테스트")
    void getDesignerBadgesByCustomerSuccessCase() {
        // Arrange
        Long designerId = 1L;

        Badge mockBadge1 = mock(Badge.class);
        Badge mockBadge2 = mock(Badge.class);

        when(mockBadge1.getIsRepresentativeBadge()).thenReturn(true);
        when(mockBadge2.getIsRepresentativeBadge()).thenReturn(false);

        when(designerPort.getAcquiredBadges(designerId)).thenReturn(List.of(mockBadge1, mockBadge2));

        // Act
        GetDesignerBadgesForCustomerResult result = customerService.getDesignerBadgesByCustomer(designerId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.acquiredBadges().size());
        assertEquals(1, result.representativeBadges().size());
        verify(designerPort).getAcquiredBadges(designerId);
    }

/*
============================================================
========================Failure Test========================
============================================================
*/

@Test
@DisplayName("2.1 고객이 존재하지 않을 때 파일 업로드 예외 발생 테스트")
void customerProfileImageUploadFailureCase() {
    // Arrange
    Long customerId = 1L;
    MultipartFile mockFile = mock(MultipartFile.class);

    // Mocking: Customer가 존재하지 않을 때
    when(customerPort.getByCustomerIdWithoutPuppies(customerId))
            .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

    // Act & Assert
    assertThatThrownBy(() -> customerService.uploadProfileImage(customerId, mockFile))
            .isInstanceOf(PeautyException.class)
            .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_USER)
            .hasMessage(PeautyResponseCode.NOT_EXIST_USER.getMessage());
}


    @Test
    @DisplayName("2.2 고객이 존재하지 않을 때 프로필 정보 조회 예외 발생 테스트")
    void getCustomerProfileFailureCase() {
        // Arrange
        Long customerId = 1L;

        // Mocking: 고객이 존재하지 않을 때 예외 발생
        when(customerPort.getByCustomerIdWithoutPuppies(customerId))
                .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.getCustomerProfile(customerId))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_USER)
                .hasMessage(PeautyResponseCode.NOT_EXIST_USER.getMessage());

        // Verify: Port 메서드 호출 확인
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);
    }


    @Test
    @DisplayName("2.3 유효하지 않은 데이터로 인한 예외 발생 테스트")
    void updateCustomerProfileFailureCase() {
        // Arrange
        Long customerId = 1L;
        UpdateCustomerProfileCommand invalidCommand = new UpdateCustomerProfileCommand(
                null, // name 누락
                "위진영",
                "010-1234-5678",
                "서울특별시 송파구",
                "http://example.com/image.jpg"
        );

        // Mocking: 필수 필드 누락으로 예외 발생
        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        doThrow(new PeautyException(PeautyResponseCode.WRONG_PARAMETER))
                .when(mockCustomer).updateName(null);

        // Act & Assert
        assertThatThrownBy(() -> customerService.updateCustomerProfile(customerId, invalidCommand))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.WRONG_PARAMETER)
                .hasMessage(PeautyResponseCode.WRONG_PARAMETER.getMessage());
    }



    @Test
    @DisplayName("2.4 중복된 닉네임으로 예외 발생 테스트")
    void checkCustomerNicknameDuplicatedFailureCase() {
        // Arrange
        String duplicateNickname = "유레카";

        // Mocking: 닉네임 중복 시
        doThrow(new PeautyException(PeautyResponseCode.ALREADY_EXIST_NICKNAME))
                .when(customerPort).checkCustomerNicknameDuplicated(duplicateNickname);

        // Act & Assert
        assertThatThrownBy(() -> customerService.checkCustomerNicknameDuplicated(duplicateNickname))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_EXIST_NICKNAME)
                .hasMessage(PeautyResponseCode.ALREADY_EXIST_NICKNAME.getMessage());
    }


    @Test
    @DisplayName("2.5 디자이너가 존재하지 않을 때 예외 발생 테스트")
    void getDesignerBadgesByCustomerFailureCase() {
        // Arrange
        Long designerId = 2L;

        // Mocking: 디자이너가 존재하지 않을 때
        when(designerPort.getAcquiredBadges(designerId))
                .thenThrow(new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.getDesignerBadgesByCustomer(designerId))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_EXIST_DESIGNER)
                .hasMessage(PeautyResponseCode.NOT_EXIST_DESIGNER.getMessage());
    }

    @Test
    @DisplayName("2.6 프로필 이미지 업로드 실패 시 예외 발생")
    void uploadProfileImage_ShouldThrowException_WhenImageUploadFails() {
        // Arrange
        Long customerId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);

        Customer mockCustomer = mock(Customer.class);
        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);

        // Mocking: 이미지 업로드 실패
        when(internalPort.uploadImage(mockFile))
                .thenThrow(new PeautyException(PeautyResponseCode.IMAGE_UPLOAD_FAIl));

        // Act & Assert
        assertThatThrownBy(() -> customerService.uploadProfileImage(customerId, mockFile))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.IMAGE_UPLOAD_FAIl)
                .hasMessage(PeautyResponseCode.IMAGE_UPLOAD_FAIl.getMessage());
    }


}