package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
    GetCustomerProfileResult getCustomerProfile(Long userId);
    UpdateCustomerProfileResult updateCustomerProfile(Long userId, UpdateCustomerProfileCommand command);
    void checkCustomerNicknameDuplicated(String nickname);
}
