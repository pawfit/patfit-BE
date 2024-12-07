package com.peauty.designer.presentation.controller.designer;

import com.peauty.designer.business.designer.DesignerService;
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
    @GetMapping("/{userId}/account")
    @Operation(summary = "디자이너 계정 정보 조회", description = "디자이너의 계정 정보 조회 API 진입점입니다.")
    public GetDesignerAccountResponse getDesignerAccount(@PathVariable Long userId){
        GetDesignerAccountResult result = designerService.getDesignerAccount(userId);
        return GetDesignerAccountResponse.from(result);
    }
    // 디자이너 프로필 수정
    @PutMapping("/{userId}/account")
    @Operation(summary = "디자이너 계정 정보 수정", description = "디자이너의 계정 정보 수정 API 진입점입니다.")
    public UpdateDesignerAccountResponse updateDesignerAccount(@PathVariable Long userId, @RequestBody UpdateDesignerAccountRequest request){
        UpdateDesignerAccountResult result = designerService.updateDesignerAccount(userId, request.toCommand());
        return UpdateDesignerAccountResponse.from(result);
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

    @GetMapping("/{userId}/badges")
    @Operation(summary = " 디자이너 본인 뱃지 조회", description =  "디자이너 본인의 뱃지를 조회하는 API 진입점입니다.")
    public GetDesignerBadgesResponse getDesignerBadges(@PathVariable Long userId) {
        GetDesignerBadgesResult result = designerService.getDesignerBadges(userId);
        return GetDesignerBadgesResponse.from(result);
    }

    @PutMapping("/{userId}/badges/{badgeId}/representative")
    @Operation(summary = "디자이너 대표 뱃지 설정", description = "디자이너가 본인이 가진 뱃지 중 대표 뱃지로 설정하는 API입니다.")
    public UpdateRepresentativeBadgeResponse updateRepresentativeBadge(@PathVariable Long userId,
                                                                       @PathVariable Long badgeId,
                                                                       @RequestBody UpdateRepresentativeBadgeRequest request){

        UpdateRepresentativeBadgeResult result = designerService.updateRepresentativeBadge(userId, badgeId, request.toCommand());
        return UpdateRepresentativeBadgeResponse.from(result);
    }


}

