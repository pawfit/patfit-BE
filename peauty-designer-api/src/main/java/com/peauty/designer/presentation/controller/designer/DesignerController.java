package com.peauty.designer.presentation.controller.designer;

import com.peauty.designer.business.designer.DesignerService;
import com.peauty.designer.business.designer.dto.UploadProfileImageResult;
import com.peauty.designer.presentation.controller.designer.dto.UploadProfileImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Designer", description = "Designer API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class DesignerController {

    private final DesignerService designerService;

    @PostMapping(value = "/{userId}/profile/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "디자이너 프로필 이미지 업로드", description = "디자이너 프로필 이미지 업로드 API 진입점입니다.")
    public UploadProfileImageResponse uploadProfileImage(@PathVariable Long userId, @RequestParam("image") MultipartFile image) {
        UploadProfileImageResult result = designerService.uploadProfileImage(userId, image);
        return UploadProfileImageResponse.from(result);
    }
}

