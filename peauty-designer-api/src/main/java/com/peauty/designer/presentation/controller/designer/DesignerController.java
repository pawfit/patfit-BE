package com.peauty.designer.presentation.controller.designer;

import com.peauty.designer.business.designer.DesignerService;
import com.peauty.designer.business.designer.UpdateDesignerWorkspaceResult;
import com.peauty.designer.business.designer.dto.*;
import com.peauty.designer.presentation.controller.designer.dto.*;
import io.swagger.v3.oas.annotations.Operation;
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
    // 디자이너 프로필 조회
    @GetMapping("/{userId}/profile")
    @Operation(summary = "디자이너 프로필 조회", description = "디자이너의 프로필 조회 API 진입점입니다.")
    public GetDesignerProfileResponse getDesignerProfile(@PathVariable Long userId){
        GetDesignerProfileResult result = designerService.getDesignerProfile(userId);
        return GetDesignerProfileResponse.from(result);
    }
    // 디자이너 프로필 수정
    @PutMapping("/{userId}/profile")
    @Operation(summary = "디자이너 프로필 수정", description = "디자이너의 프로필 수정 API 진입점입니다.")
    public UpdateDesignerProfileResponse updateDesignerProfile(@PathVariable Long userId, @RequestBody UpdateDesignerProfileRequest request){
        UpdateDesignerProfileResult result = designerService.updateDesignerProfile(userId, request.toCommand());
        return UpdateDesignerProfileResponse.from(result);
    }
    // 닉네임 중복 체크
    @GetMapping("/check")
    public CheckDesignerNicknameDuplicatedResponse checkDesignerNicknameDuplicatedResponse(@RequestParam String nickname) {
        designerService.checkDesignerNicknameDuplicated(nickname);
        return new CheckDesignerNicknameDuplicatedResponse("사용해도 좋은 닉네임입니다.");
    }
    // 디자이너 워크 스페이스 등록
    @PostMapping(value = "/{userId}/shop")
    @Operation(summary = "디자이너 워크 스페이스 등록", description = "디자이너 워크 스페이스 등록 API 진입점입니다.")
    public CreateDesignerWorkspaceResponse createDesignerWorkspace(@PathVariable Long userId, @RequestBody CreateDesignerWorkspaceRequest request) {
        CreateDesignerWorkspaceResult result = designerService.createDesignerWorkspace(userId, request.toCommand());
        return CreateDesignerWorkspaceResponse.from(result);
    }

    @GetMapping(value ="/{userId}/shop")
    @Operation(summary = "디자이너 워크 스페이스 조회", description = "디자이너 워크 스페이스 조회 API 진입점입니다.")
    public GetDesignerWorkspaceResponse getDesignerWorkspace(@PathVariable Long userId) {
        GetDesignerWorkspaceResult result = designerService.getDesignerWorkspace(userId);
        return GetDesignerWorkspaceResponse.from(result);
    }

    @PutMapping(value = "/{userId}/shop")
    @Operation(summary = "디자이너 워크 스페이스 수정", description = "디자이너 워크 스페이스 수정 API 진입점입니다.")
    public UpdateDesignerWorkspaceResponse updateDesignerWorkspace(@PathVariable Long userId, @RequestBody UpdateDesignerWorkspaceRequest request){
        UpdateDesignerWorkspaceResult result = designerService.updateDesignerWorkspace(userId, request.toCommand());
        return UpdateDesignerWorkspaceResponse.from(result);
    }
}

