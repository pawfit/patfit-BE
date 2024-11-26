package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.UploadProfileImageResult;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
}
