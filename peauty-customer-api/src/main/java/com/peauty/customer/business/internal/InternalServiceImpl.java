package com.peauty.customer.business.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalServiceImpl implements InternalService {

    private final InternalPort internalPort;

    @Override
    public String uploadImage(MultipartFile image) {
        return internalPort.uploadImage(image);
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> images) {
        return internalPort.uploadImages(images);
    }
}
