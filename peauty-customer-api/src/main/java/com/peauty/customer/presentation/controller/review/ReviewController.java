package com.peauty.customer.presentation.controller.review;

import com.peauty.customer.business.review.ReviewService;
import com.peauty.customer.business.review.dto.*;
import com.peauty.customer.presentation.controller.review.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Review", description = "Review API")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews")
    @Operation(summary = "리뷰 작성", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 작성하는 API 진입점입니다.")
    public RegisterReviewResponse registerReview(@PathVariable Long userId,
                                                 @PathVariable Long puppyId,
                                                 @PathVariable Long processId,
                                                 @PathVariable Long threadId,
                                                 @RequestBody RegisterReviewRequest request) {
        RegisterReviewResult result = reviewService.registerReview(
                userId,
                puppyId,
                processId,
                threadId,
                request.toCommand());
        return RegisterReviewResponse.from(result);
    }

    @PutMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 수정하는 API 진입점입니다.")
    public UpdateReviewResponse updateReview(@PathVariable Long userId,
                                             @PathVariable Long puppyId,
                                             @PathVariable Long processId,
                                             @PathVariable Long threadId,
                                             @PathVariable Long reviewId,
                                             @RequestBody UpdateReviewRequest request) {
        UpdateReviewResult result = reviewService.updateReview(
                userId,
                puppyId,
                processId,
                threadId,
                reviewId,
                request.toCommand());
        return UpdateReviewResponse.from(result);

    }

    @DeleteMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 삭제하는 API 진입점입니다.")
    public DeleteReviewResponse deleteReview(@PathVariable Long userId,
                                             @PathVariable Long puppyId,
                                             @PathVariable Long processId,
                                             @PathVariable Long threadId,
                                             @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, puppyId, threadId, processId, reviewId);
        return new DeleteReviewResponse("리뷰가 삭제되었습니다.");
    }

    @GetMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "고객이 자신이 작성한 리뷰를 상세 조회하는 API 진입점입니다.")
    public GetReviewDetailResponse getReviewDetail(@PathVariable Long userId,
                                                   @PathVariable Long puppyId,
                                                   @PathVariable Long processId,
                                                   @PathVariable Long threadId,
                                                   @PathVariable Long reviewId){
        GetReviewDetailResult result = reviewService.getReviewDetail(userId, puppyId, threadId, processId, reviewId);
        return GetReviewDetailResponse.from(result);
    }

    @GetMapping("/users/{designerId}/shop/reviews")
    @Operation(summary = "디자이너 리뷰 전체 조회", description = "디자이너의 리뷰를 조회하는 API 진입점입니다.")
    public GetDesignerReviewsResponse getDesignerReviews(@PathVariable Long designerId) {

        GetDesignerReviewsResult result = reviewService.getDesignerReviews(designerId);
        return GetDesignerReviewsResponse.from(result);
    }

/* TODO: 추후 프론트 측에서 요청 시
    @GetMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/estimate")
    @Operation(summary = "견적서 데이터 조회", description = "고객이 자신의 강아지를 미용한 디자이너와의 견적서를 간단히 조회하는 API입니다.")
    public GetEstimateDataResponse getEstimateData(@PathVariable Long userId,
                                                   @PathVariable Long puppyId,
                                                   @PathVariable Long threadId,
                                                   @PathVariable Long processId) {
        GetEstimateDataResult result = reviewService.getEstimateData(userId, puppyId, threadId, processId);
        return GetEstimateDataResponse.from(result);
    }*/
}