package com.peauty.customer.presentation.controller.customer;

import com.peauty.customer.business.customer.CustomerService;
import com.peauty.customer.business.customer.dto.UploadProfileImageResult;
import com.peauty.customer.presentation.controller.customer.dto.UploadProfileImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Customer", description = "Customer API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/{userId}/profile/image")
    @Operation(summary = "고객 프로필 이미지 업로드", description = "고객의 프로필 이미지 업로드 API 진입점입니다.")
    public UploadProfileImageResponse uploadProfileImage(@PathVariable Long userId, @RequestParam("image") MultipartFile image) {
        UploadProfileImageResult result = customerService.uploadProfileImage(userId, image);
        return UploadProfileImageResponse.from(result);
    }
}
