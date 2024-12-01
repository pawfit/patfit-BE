package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.GetDesignerProfileResult;
import com.peauty.designer.business.designer.dto.UpdateDesignerProfileCommand;
import com.peauty.designer.business.designer.dto.UpdateDesignerProfileResult;
import com.peauty.designer.business.designer.dto.UploadProfileImageResult;
import com.peauty.designer.business.designer.dto.CreateDesignerWorkspaceCommand;
import com.peauty.designer.business.designer.dto.CreateDesignerWorkspaceResult;
import org.springframework.web.multipart.MultipartFile;

public interface DesignerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
    GetDesignerProfileResult getDesignerProfile(Long designerId);
    UpdateDesignerProfileResult updateDesignerProfile(Long designerId, UpdateDesignerProfileCommand command);
    void checkDesignerNicknameDuplicated(String nickname);
    CreateDesignerWorkspaceResult createDesignerWorkspace(Long userId, CreateDesignerWorkspaceCommand command);
}
