package com.peauty.designer.presentation.controller.bidding;

import com.peauty.designer.business.bidding.DesignerBiddingService;
import com.peauty.designer.business.bidding.dto.SendEstimateResult;
import com.peauty.designer.presentation.controller.bidding.dto.SendEstimateRequest;
import com.peauty.designer.presentation.controller.bidding.dto.SendEstimateResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class DesignerBiddingController {

    private final DesignerBiddingService designerBiddingService;

    @PostMapping("/{userId}/processes/{processId}/biddings/{biddingId}")
    @Operation(summary = "견적 제안서 응답", description = "고객의 요청에 대한 견적서를 전송합니다.")
    public SendEstimateResponse sendEstimate(
            @PathVariable Long userId,
            @PathVariable Long processId,
            @PathVariable Long biddingId,
            @RequestBody SendEstimateRequest request
    ) {
        SendEstimateResult result = designerBiddingService.sendEstimate(
                userId,
                processId,
                biddingId,
                request.toCommand());
        return SendEstimateResponse.from(result);
    }
}
