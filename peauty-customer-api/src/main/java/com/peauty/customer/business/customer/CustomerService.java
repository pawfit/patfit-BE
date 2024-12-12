package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.workspace.dto.GetDesignerWorkspaceResult;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    UploadProfileImageResult uploadProfileImage(Long customerId, MultipartFile file);
    GetCustomerProfileResult getCustomerProfile(Long customerId);
    UpdateCustomerProfileResult updateCustomerProfile(Long customerId, UpdateCustomerProfileCommand command);
    void checkCustomerNicknameDuplicated(String nickname);
    GetAroundWorkspacesResult getAroundWorkspaces(Long customerId);
    GetDesignerBadgesForCustomerResult getDesignerBadgesByCustomer(Long designerId);
}
