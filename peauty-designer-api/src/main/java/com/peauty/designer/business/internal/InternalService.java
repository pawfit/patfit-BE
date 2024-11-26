package com.peauty.designer.business.internal;

import org.springframework.web.multipart.MultipartFile;

public interface InternalService {

    String uploadImage(MultipartFile image);
}
