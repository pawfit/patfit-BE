package customer;

import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.customer.business.customer.CustomerServiceImpl;
import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private WorkspacePort workspacePort;

    @Mock
    private DesignerPort designerPort;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("고객 프로필 정보를 정상적으로 조회한다")
    void getCustomerProfile_ShouldReturnProfile() {
        // Arrange
        Long customerId = 1L;
        Customer mockCustomer = Customer.builder()
                .customerId(customerId)
                .socialId("3000")
                .socialPlatform(SocialPlatform.KAKAO)
                .name("John Doe")
                .status(Status.ACTIVE)
                .role(Role.ROLE_CUSTOMER)
                .nickname("johnny")
                .profileImageUrl("http://example.com/profile.jpg")
                .address("Seoul Gangnam")
                .phoneNumber("010-1234-5678")
                .build();

        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);

        System.out.println("===========" + customerPort.getByCustomerIdWithoutPuppies(customerId));

        // Act
        GetCustomerProfileResult result = customerService.getCustomerProfile(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertEquals("John Doe", result.name());
        assertEquals("johnny", result.nickname());
        assertEquals("http://example.com/profile.jpg", result.profileImageUrl());
        assertEquals("Seoul Gangnam", result.address());
        assertEquals("010-1234-5678", result.phoneNumber());
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);
    }

    @Test
    @DisplayName("프로필 이미지를 업로드하면 URL이 저장되고 고객 정보가 업데이트된다")
    void uploadProfileImage_ShouldUpdateAndSaveCustomer() {
        // Arrange
        Long customerId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        String uploadedUrl = "http://example.com/image.jpg";
        Customer mockCustomer = Customer.builder()
                .customerId(customerId)
                .build();

        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(internalPort.uploadImage(file)).thenReturn(uploadedUrl);
        when(customerPort.save(mockCustomer)).thenReturn(mockCustomer);

        // Act
        UploadProfileImageResult result = customerService.uploadProfileImage(customerId, file);

        // Assert
        assertNotNull(result);
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);
        verify(internalPort).uploadImage(file);
        verify(customerPort).save(mockCustomer);
        assertEquals(uploadedUrl, result.uploadedProfileImageUrl());
    }

    @Test
    @DisplayName("고객 프로필 정보를 업데이트하면 변경 사항이 저장된다")
    void updateCustomerProfile_ShouldUpdateAndSaveCustomer() {
        // Arrange
        Long customerId = 1L;
        UpdateCustomerProfileCommand command = new UpdateCustomerProfileCommand(
                "Updated Name", "010-5678-1234", "updatedNickname",
                "http://example.com/updated-profile.jpg", "Updated Address");
        Customer mockCustomer = Customer.builder()
                .customerId(customerId)
                .build();

        when(customerPort.getByCustomerIdWithoutPuppies(customerId)).thenReturn(mockCustomer);
        when(customerPort.save(mockCustomer)).thenReturn(mockCustomer);

        // Act
        UpdateCustomerProfileResult result = customerService.updateCustomerProfile(customerId, command);

        // Assert
        assertNotNull(result);
        verify(customerPort).getByCustomerIdWithoutPuppies(customerId);
        verify(mockCustomer).updateName(command.name());
        verify(mockCustomer).updatePhoneNumber(command.phoneNumber());
        verify(mockCustomer).updateNickname(command.nickname());
        verify(mockCustomer).updateProfileImageUrl(command.profileImageUrl());
        verify(mockCustomer).updateAddress(command.address());
        verify(customerPort).save(mockCustomer);
    }

    @Test
    @DisplayName("고객 닉네임 중복 여부를 확인한다")
    void checkCustomerNicknameDuplicated_ShouldInvokePortMethod() {
        // Arrange
        String nickname = "testNickname";

        doNothing().when(customerPort).checkCustomerNicknameDuplicated(nickname);

        // Act
        customerService.checkCustomerNicknameDuplicated(nickname);

        // Assert
        verify(customerPort, times(1)).checkCustomerNicknameDuplicated(nickname);
    }

    @Test
    @DisplayName("고객 주소를 기반으로 근처 워크스페이스 정보를 조회한다")
    void getAroundWorkspaces_ShouldReturnNearbyWorkspaces() {
        // Arrange
        Long customerId = 1L;
        Customer mockCustomer = Customer.builder()
                .customerId(customerId)
                .address("Seoul Gangnam")
                .build();
        Workspace mockWorkspace = mock(Workspace.class);
        Designer mockDesigner = mock(Designer.class);
        Badge mockBadge = mock(Badge.class);

        when(customerPort.getCustomerById(customerId)).thenReturn(mockCustomer);
        when(workspacePort.findAllWorkspaceByAddress("Seoul Gangnam")).thenReturn(List.of(mockWorkspace));
        when(workspacePort.findDesignerById(anyLong())).thenReturn(mockDesigner);
        when(designerPort.getRepresentativeBadges(anyLong())).thenReturn(List.of(mockBadge));

        // Act
        GetAroundWorkspacesResult result = customerService.getAroundWorkspaces(customerId);

        // Assert
        assertNotNull(result);
        verify(customerPort).getCustomerById(customerId);
        verify(workspacePort).findAllWorkspaceByAddress("Seoul Gangnam");
        verify(workspacePort).findDesignerById(anyLong());
        verify(designerPort).getRepresentativeBadges(anyLong());
    }
}