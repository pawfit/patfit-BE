package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface DesignerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
    GetDesignerAccountResult getDesignerAccount(Long designerId);
    UpdateDesignerAccountResult updateDesignerAccount(Long designerId, UpdateDesignerAccountCommand command);
    void checkDesignerNicknameDuplicated(String nickname);
    CreateDesignerWorkspaceResult createDesignerWorkspace(Long userId, CreateDesignerWorkspaceCommand command);
    GetDesignerWorkspaceResult getDesignerWorkspace(Long workspaceId);
    UpdateDesignerWorkspaceResult updateDesignerWorkspace(Long userId, UpdateDesignerWorkspaceCommand command);
    GetDesignerBadgesResult getDesignerBadges(Long designerId);
}
