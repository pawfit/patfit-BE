package com.peauty.designer.presentation.controller.bidding;

import com.peauty.designer.business.bidding.DesignerBiddingService;
import com.peauty.designer.business.bidding.dto.*;
import com.peauty.designer.presentation.controller.bidding.dto.*;
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

    @GetMapping("/{userId}/bidding/processes/threads/schedule")
    @Operation(summary = "디자이너 스케줄 조회", description = "디자이너의 프로필, 미용 , 오늘의 예약들을 가져옵니다.")
    public GetDesignerScheduleResponse getDesignerSchedule(@PathVariable Long userId) {
        GetDesignerScheduleResult result = designerBiddingService.getDesignerSchedule(userId);
        return GetDesignerScheduleResponse.from(result);
    }

    @GetMapping("/{userId}/bidding/processes/threads/above-step3")
    @Operation(summary = "확정 견적 조회", description = "디자이너와 연관된 스레드들 중 Step 3 이상의 스레드들을 조회.")
    public GetStep3AboveThreadsResponse getStep3AboveThreads(@PathVariable Long userId) {
        GetThreadsByStepResult result = designerBiddingService.getStep3AboveThreads(userId);
        return GetStep3AboveThreadsResponse.from(result);
    }

    @GetMapping("/{userId}/bidding/processes/threads/step2")
    @Operation(summary = "보낸 견적 조회", description = "디자이너와 연관된 스레드들 중 Step 2 스레드들을 조회.")
    public GetStep2ThreadsResponse getStep2Threads(@PathVariable Long userId) {
        GetThreadsByStepResult result = designerBiddingService.getStep2Threads(userId);
        return GetStep2ThreadsResponse.from(result);
    }

    @GetMapping("/{userId}/bidding/processes/threads/step1")
    @Operation(summary = "받은 요청 조회", description = "디자이너와 연관된 스레드들 중 Step 1 스레드들을 조회.")
    public GetStep1ThreadsResponse getStep1Threads(@PathVariable Long userId) {
        GetThreadsByStepResult result = designerBiddingService.getStep1Threads(userId);
        return GetStep1ThreadsResponse.from(result);
    }
}
