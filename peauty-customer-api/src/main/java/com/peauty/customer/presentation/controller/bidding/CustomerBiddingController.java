package com.peauty.customer.presentation.controller.bidding;

import com.peauty.customer.business.bidding.CustomerBiddingService;
import com.peauty.customer.business.bidding.dto.*;
import com.peauty.customer.presentation.controller.bidding.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Customer Bidding API", description = "사용자와 관련된 입찰 프로세스 API")
public class CustomerBiddingController {

    private final CustomerBiddingService customerBiddingService;

    @PostMapping("/{userId}/puppies/{puppyId}/biddings")
    @Operation(summary = "입찰 프로세스 시작", description = "디자이너들에게 견적 제안을 전송하면서 입찰 프로세스를 시작합니다.")
    public SendEstimateProposalResponse initProcessWithSendEstimateProposal(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @RequestBody SendEstimateProposalRequest request
    ) {
        SendEstimateProposalResult result = customerBiddingService.sendEstimateProposal(userId, puppyId, request.toCommand());
        return SendEstimateProposalResponse.from(result);
    }

    @PostMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/accept")
    @Operation(summary = "견적 수락", description = "디자이너가 제안한 견적을 수락합니다.")
    public AcceptEstimateResponse acceptEstimate(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId,
            @PathVariable Long threadId
    ) {
        AcceptEstimateResult result = customerBiddingService.acceptEstimate(
                userId,
                puppyId,
                processId,
                threadId
        );
        return AcceptEstimateResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}")
    @Operation(summary = "견적요청서 & 견적서 상세정보 조회", description = "해당하는 견적요청서와 견적서의 상세정보를 조회합니다.")
    public GetEstimateAndProposalDetailsResponse getEstimateAndProposalDetails(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId,
            @PathVariable Long threadId
    ) {
        GetEstimateAndProposalDetailsResult result = customerBiddingService.getEstimateAndProposalDetails(
                userId,
                puppyId,
                processId,
                threadId
        );
        return GetEstimateAndProposalDetailsResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads")
    @Operation(summary = "견적서 응답 미용사 워크스페이스 프로필 조회", description = "해당하는 견적요청서에 견적서를 보내준 미용사들의 워크스페이스 프로필을 조회합니다.")
    public GetEstimateDesignerWorkspaceProfilesResponse getEstimateDesignersWorkSpaceProfiles(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId
    ) {
        GetEstimateDesignerWorkspaceProfilesResult result = customerBiddingService.getEstimateDesignerWorkspaceProfiles(
                userId,
                puppyId,
                processId
        );
        return GetEstimateDesignerWorkspaceProfilesResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}")
    @Operation(summary = "견적요청서 디테일 조회", description = "해당하는 견적요청서의 디테일을 조회합니다")
    public GetEstimateProposalDetailResponse getEstimateProposalDetail(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId
    ) {
        GetEstimateProposalDetailResult result = customerBiddingService.getEstimateProposalDetail(
                userId,
                puppyId,
                processId
        );
        return GetEstimateProposalDetailResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/biddings/processes/complete")
    @Operation(summary = "완료 프로세스 조회", description = "해당 고객의 모든 프로세스 중 완료된 상태의 프로세스들을 조회합니다.")
    public GetAllCompletedProcess getAllCompletedProcess(@PathVariable Long userId) {
        return GetAllCompletedProcess.from(
                customerBiddingService.getAllCompletedProcess(userId)
        );
    }
}