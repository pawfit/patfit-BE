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
@Tag(name = "Bidding", description = "Bidding API")
public class CustomerBiddingController {

    private final CustomerBiddingService customerBiddingService;

    @PostMapping("/{userId}/puppies/{puppyId}/bidding/processes")
    @Operation(summary = "견적요청서 보내기", description = "선택한 디자이너들과 스레드를 시작함과 함께 프로세스를 시작")
    public SendEstimateProposalResponse initProcessWithSendEstimateProposal(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @RequestBody SendEstimateProposalRequest request
    ) {
        SendEstimateProposalResult result = customerBiddingService.sendEstimateProposal(userId, puppyId, request.toCommand());
        return SendEstimateProposalResponse.from(result);
    }

    @PostMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/accept")
    @Operation(summary = "견적 수락", description = "해당 스레드의 Step 을 3으로 만듬.")
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
    @Operation(summary = "견적요청서 & 견적서 상세정보 조회", description = "해당하는 프로세스와 스레드와 연관된 견적요청서와 견적서의 상세정보를 조회.")
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

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/threads/above-step3")
    @Operation(summary = "확정견적 조회", description = "강아지의 모든 프로세스와 그 프로세스들에 속해있는 스레드들 중 Step 3 이상 스레드들을 조회.")
    public GetAllStep3AboveThreadsResponse getAllStep3AboveThreads(
            @PathVariable Long userId,
            @PathVariable Long puppyId
    ) {
        GetAllStep3AboveThreadsResult result = customerBiddingService.getAllStep3AboveThreads(
                userId,
                puppyId
        );
        return GetAllStep3AboveThreadsResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/threads/step2")
    @Operation(summary = "받은견적 조회", description = "강아지의 현재 진행 중인 프로세스와 해당 프로세스에 속해있는 스레드들 중 Step 2 스레드들을 조회.")
    public GetOngoingProcessWithThreadsResponse getOngoingProcessWithStep2Threads(
            @PathVariable Long userId,
            @PathVariable Long puppyId
    ) {
        GetOngoingProcessWithThreadsResult result = customerBiddingService.getOngoingProcessWithStep2Threads(
                userId,
                puppyId
        );
        return GetOngoingProcessWithThreadsResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/threads/step1")
    @Operation(summary = "견적요청 조회", description = "강아지의 현재 진행 중인 프로세스와 해당 프로세스에 속해있는 스레드들 중 Step 1 스레드들을 조회.")
    public GetOngoingProcessWithThreadsResponse getOngoingProcessWithStep1Threads(
            @PathVariable Long userId,
            @PathVariable Long puppyId
    ) {
        GetOngoingProcessWithThreadsResult result = customerBiddingService.getOngoingProcessWithStep1Threads(
                userId,
                puppyId
        );
        return GetOngoingProcessWithThreadsResponse.from(result);
    }

    @GetMapping("/{userId}/puppies/{puppyId}/bidding/processes/{processId}")
    @Operation(summary = "견적요청서 디테일 조회", description = "해당하는 프로세스와 연관된 견적요청서의 디테일을 조회.")
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
}