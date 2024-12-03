package com.peauty.designer.business.internal;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InternalService {

    String uploadImage(MultipartFile image);
    List<String> uploadImages(List<MultipartFile> image);
}
