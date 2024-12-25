package com.peauty.addy.dalle;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "dalle-edit-image", url = "https://api.openai.com/v1")
public interface DalleClient {
    @PostMapping(value = "/images/edits", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DalleEditImageResponse editImage(
            @RequestPart("image") MultipartFile image,
            @RequestPart("mask") MultipartFile mask,
            @RequestPart("model") String model,
            @RequestPart("prompt") String prompt,
            @RequestPart("n") Integer n,
            @RequestPart("size") String size,
            @RequestHeader("Authorization") String bearerToken
    );
}