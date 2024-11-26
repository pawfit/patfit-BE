package com.peauty.customer.presentation.controller.puppy;

import com.peauty.customer.business.puppy.PuppyService;
import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.customer.business.puppy.dto.RegisterPuppyResult;
import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;
import com.peauty.customer.presentation.controller.puppy.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Puppy", description = "Puppy API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class PuppyController {

    private final PuppyService puppyService;

    @PostMapping("/{userId}/puppies")
    @Operation(summary = "반려견 등록", description = "반려견을 등록합니다.")
    public PuppyDetailResponse registerPuppy(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId, @RequestBody AddPuppyRequest req) {
        AddPuppyCommand command = req.toCommand(userId);
        RegisterPuppyResult result = puppyService.addPuppy(command);
        return PuppyDetailResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 조회", description = "특정 반려견을 조회합니다.")
    public PuppyDetailResponse getPuppyDetail(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "반려견 ID", example = "1") @PathVariable Long puppyId){
        RegisterPuppyResult result = puppyService.getPuppy(userId, puppyId);
//        return ResponseEntity.ok(PuppyDetailResponse.from(result));
        return PuppyDetailResponse.from(result);
    }

    @PutMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 수정", description = "반려견을 수정합니다.")
    public UpdatePuppyResponse updatePuppy(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "반려견 ID", example = "1") @PathVariable Long puppyId,
            @RequestBody UpdatePuppyRequest req
    ) {
        UpdatePuppyCommand command = req.toCommand(userId, puppyId);
        RegisterPuppyResult result = puppyService.updatePuppy(command);
        return UpdatePuppyResponse.from(result);
    }

    @DeleteMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 삭제", description = "반려견을 삭제합니다.")
    public DeletePuppyResponse deletePuppy(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "반려견 ID", example = "1") @PathVariable Long puppyId
    ) {
        puppyService.deletePuppy(userId, puppyId);
        return new DeletePuppyResponse("삭제되었습니다.");
//        return ResponseEntity.noContent().build();
    }

}