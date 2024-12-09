package com.peauty.designer.presentation.controller.bidding;

import com.peauty.designer.business.bidding.DesignerBiddingService;
import com.peauty.designer.business.bidding.dto.CompleteGroomingResult;
import com.peauty.designer.business.bidding.dto.GetEstimateAndProposalDetailsResult;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;
import com.peauty.designer.presentation.controller.bidding.dto.CompleteGroomingResponse;
import com.peauty.designer.presentation.controller.bidding.dto.GetEstimateAndProposalDetailsResponse;
import com.peauty.designer.presentation.controller.bidding.dto.SendEstimateRequest;
import com.peauty.designer.presentation.controller.bidding.dto.SendEstimateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
@Tag(name = "Designer Bidding API", description = "디자이너와 관련된 입찰 프로세스 API")
public class DesignerBiddingController {

    private final DesignerBiddingService designerBiddingService;

    @PostMapping("/{userId}/bidding/processes/{processId}/threads/{threadId}/send")
    @Operation(summary = "견적 제안서 응답", description = "고객의 요청에 대한 견적서를 전송합니다.")
    public SendEstimateResponse sendEstimate(
            @PathVariable Long userId,
            @PathVariable Long processId,
            @PathVariable Long threadId,
            @RequestBody SendEstimateRequest request
    ) {
        SendEstimateResult result = designerBiddingService.sendEstimate(
                userId,
                processId,
                threadId,
                request.toCommand());
        return SendEstimateResponse.from(result);
    }

    @PostMapping("/{userId}/bidding/processes/{processId}/threads/{threadId}/complete")
    @Operation(summary = "미용 완료", description = "미용사가 오프라인에서 미용을 완료했습니다.")
    public CompleteGroomingResponse completeGrooming(
            @PathVariable Long userId,
            @PathVariable Long processId,
            @PathVariable Long threadId
    ) {
        CompleteGroomingResult result = designerBiddingService.completeGrooming(
                userId,
                processId,
                threadId
        );
        return CompleteGroomingResponse.from(result);
    }

    @GetMapping("/{userId}/bidding/processes/{processId}/threads/{threadId}")
    @Operation(summary = "견적요청서 & 견적서 상세정보 조회", description = "해당하는 견적요청서와 견적서의 상세정보를 조회합니다.")
    public GetEstimateAndProposalDetailsResponse getEstimateAndProposalDetails(
            @PathVariable Long userId,
            @PathVariable Long processId,
            @PathVariable Long threadId
    ) {
        GetEstimateAndProposalDetailsResult result = designerBiddingService.getEstimateAndProposalDetails(
                userId,
                processId,
                threadId
        );
        return GetEstimateAndProposalDetailsResponse.from(result);
    }
}
