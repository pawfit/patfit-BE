package com.peauty.customer.implementaion.internal;

import com.peauty.aws.S3ImageClient;
import com.peauty.customer.business.internal.InternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class InternalAdapter implements InternalPort {

    private final S3ImageClient s3ImageClient;

    @Override
    public String uploadImage(MultipartFile file) {
        return s3ImageClient.uploadImageToS3(file);
    }
}
