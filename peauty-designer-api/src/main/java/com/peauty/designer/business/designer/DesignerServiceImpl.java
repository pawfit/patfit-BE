package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.UploadProfileImageResult;
import com.peauty.designer.business.internal.InternalPort;
import com.peauty.domain.designer.Designer;
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
    public UploadProfileImageResult uploadProfileImage(Long designerId, MultipartFile file) {
        Designer designer = designerPort.getByDesignerId(designerId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        designer.updateProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(designerPort.save(designer));
    }
}
