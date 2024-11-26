package com.peauty.designer.business.internal;

import org.springframework.web.multipart.MultipartFile;

public interface InternalPort {

    String uploadImage(MultipartFile image);
}
