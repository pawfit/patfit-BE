package com.peauty.customer.business.internal;

import org.springframework.web.multipart.MultipartFile;

public interface InternalPort {
    String uploadImage(MultipartFile file);
}
