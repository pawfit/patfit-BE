package com.peauty.customer.presentation.controller.customer;

import com.peauty.customer.business.customer.CustomerService;
import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.presentation.controller.customer.dto.GetAroundWorkspacesResponse;
import com.peauty.customer.presentation.controller.customer.dto.GetCustomerProfileResponse;
import com.peauty.customer.presentation.controller.customer.dto.UploadProfileImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Customer", description = "Customer API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/{userId}/profile/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "고객 프로필 이미지 업로드", description = "고객의 프로필 이미지 업로드 API 진입점입니다.")
    public UploadProfileImageResponse uploadProfileImage(@PathVariable Long userId, @RequestParam("image") MultipartFile image) {
        UploadProfileImageResult result = customerService.uploadProfileImage(userId, image);
        return UploadProfileImageResponse.from(result);
    }

    @GetMapping("/{userId}/profile")
    @Operation(summary = "고객 프로필 조회", description = "고객의 프로필 조회 API 진입점입니다.")
    public GetCustomerProfileResponse getCustomerProfile(@PathVariable Long userId) {
        GetCustomerProfileResult result = customerService.getCustomerProfile(userId);
        return GetCustomerProfileResponse.from(result);
    }

    @PutMapping("/{userId}/profile")
    @Operation(summary = "고객 프로필 수정", description = "고객의 프로필 수정 API 진입점입니다.")
    public UpdateCustomerProfileResponse updateCustomerProfile(
            @PathVariable Long userId,
            @RequestBody UpdateCustomerProfileRequest request
    ) {
        UpdateCustomerProfileResult result = customerService.updateCustomerProfile(userId, request.toCommand());
        return UpdateCustomerProfileResponse.from(result);
    }

    @GetMapping("/check")
    @Operation(summary = "고객 닉네임 중복 체크", description = "고객의 닉네임 중복 체크 API 진입점입니다.")
    public CheckCustomerNicknameDuplicatedResponse checkCustomerNicknameDuplicated(@RequestParam String nickname) {
        customerService.checkCustomerNicknameDuplicated(nickname);
        return new CheckCustomerNicknameDuplicatedResponse("사용해도 좋은 닉네임입니다.");
    }

    @GetMapping("/{userId}/serach")
    @Operation(summary = "주변 디자이너 매장 조회", description = "고객 주소와 같은 디자이너의 매장을 조회하는 API 진입점입니다.")
    public GetAroundWorkspacesResponse getAroundWorkspaces(@PathVariable Long userId) {
        GetAroundWorkspacesResult result = customerService.getAroundWorkspaces(userId);
        return GetAroundWorkspacesResponse.from(result);
    }

}
