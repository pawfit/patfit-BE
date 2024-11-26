package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.UploadProfileImageResult;
import org.springframework.web.multipart.MultipartFile;

public interface DesignerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
}
