package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface DesignerService {
    UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file);
    GetDesignerProfileResult getDesignerProfile(Long designerId);
    UpdateDesignerProfileResult updateDesignerProfile(Long designerId, UpdateDesignerProfileCommand command);
    void checkDesignerNicknameDuplicated(String nickname);
    CreateDesignerWorkspaceResult createDesignerWorkspace(Long userId, CreateDesignerWorkspaceCommand command);
    GetDesignerWorkspaceResult getDesignerWorkspace(Long workspaceId);
    UpdateDesignerWorkspaceResult updateDesignerWorkspace(Long userId, UpdateDesignerWorkspaceCommand command);
    GetDesignerBadgesResult getDesignerBadges(Long designerId);
    UpdateRepresentativeBadgeResult updateRepresentativeBadge(Long userId, Long badgeId, UpdateRepresentativeBadgeCommand command);
}
