package com.peauty.designer.presentation.controller.internal;

import com.peauty.designer.business.internal.InternalService;
import com.peauty.designer.presentation.controller.internal.dto.UploadImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Internal", description = "Internal API")
@RequestMapping("/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final InternalService internalService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 업로드", description = "이미지 업로드 API 진입점입니다.")
    public UploadImageResponse uploadImage(@RequestPart("image") MultipartFile image) {
        String uploadedImageUrl = internalService.uploadImage(image);
        return UploadImageResponse.from(uploadedImageUrl);
    }
}
