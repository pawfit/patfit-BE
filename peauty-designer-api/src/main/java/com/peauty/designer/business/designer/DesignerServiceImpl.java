package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.UploadProfileImageResult;
import com.peauty.designer.business.internal.InternalPort;
import com.peauty.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerServiceImpl implements DesignerService {

    private final DesignerPort designerPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file) {
        User user = designerPort.getByUserId(userId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        user.uploadProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(designerPort.save(user));
    }
}
