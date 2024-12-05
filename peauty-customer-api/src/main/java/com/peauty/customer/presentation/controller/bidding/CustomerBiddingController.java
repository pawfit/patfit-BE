package com.peauty.customer.presentation.controller.bidding;

import com.peauty.customer.business.bidding.CustomerBiddingService;
import com.peauty.customer.business.bidding.dto.AcceptEstimateResult;
import com.peauty.customer.business.bidding.dto.SendEstimateProposalResult;
import com.peauty.customer.presentation.controller.bidding.dto.AcceptEstimateResponse;
import com.peauty.customer.presentation.controller.bidding.dto.SendEstimateProposalRequest;
import com.peauty.customer.presentation.controller.bidding.dto.SendEstimateProposalResponse;
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
}