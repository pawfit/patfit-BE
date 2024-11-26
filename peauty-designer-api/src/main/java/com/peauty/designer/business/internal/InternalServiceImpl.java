package com.peauty.designer.business.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class InternalServiceImpl implements InternalService {

    private final InternalPort internalPort;

    @Override
    public String uploadImage(MultipartFile image) {
        return internalPort.uploadImage(image);
    }
}
