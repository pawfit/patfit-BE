package com.peauty.addy.dalle;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DalleClientImpl implements DalleClient {
    @Override
    public DalleEditImageResponse editImage(MultipartFile image, MultipartFile mask, String model, String prompt, Integer n, String size, String bearerToken) {
        return null;
    }
}
