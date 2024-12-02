package com.peauty.customer.presentation.controller.internal;

import com.peauty.customer.business.internal.InternalService;
import com.peauty.customer.presentation.controller.internal.dto.UploadImageResponse;
import com.peauty.customer.presentation.controller.internal.dto.UploadImagesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Internal", description = "Internal API")
@RequestMapping("/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final InternalService internalService;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "단일 이미지 업로드", description = "단일 이미지 업로드 API 진입점입니다.")
    public UploadImageResponse uploadImage(@RequestPart("image") MultipartFile image) {
        String uploadedImageUrl = internalService.uploadImage(image);
        return UploadImageResponse.from(uploadedImageUrl);
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "다중 이미지 업로드", description = "다중 이미지 업로드 API 진입점입니다.")
    public UploadImagesResponse uploadImages(@RequestPart("image") List<MultipartFile> images) {
        List<String> uploadedImageUrls = internalService.uploadImages(images);
        return UploadImagesResponse.from(uploadedImageUrls);
    }
}
