package com.peauty.customer.presentation.controller.review;

import com.peauty.customer.business.review.ReviewService;
import com.peauty.customer.business.review.dto.RegisterReviewResult;
import com.peauty.customer.business.review.dto.UpdateReviewResult;
import com.peauty.customer.presentation.controller.review.dto.RegisterReviewRequest;
import com.peauty.customer.presentation.controller.review.dto.RegisterReviewResponse;
import com.peauty.customer.presentation.controller.review.dto.UpdateReviewRequest;
import com.peauty.customer.presentation.controller.review.dto.UpdateReviewResponse;
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
                                                 @PathVariable Long threadId,
                                                 @PathVariable Long processId,
                                                 @RequestBody RegisterReviewRequest request){
        RegisterReviewResult result = reviewService.registerReview(
                userId,
                puppyId,
                threadId,
                processId,
                request.toCommand());
        return RegisterReviewResponse.from(result);
    }

    @PutMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 수정하는 API 진입점입니다.")
    public UpdateReviewResponse updateReview(@PathVariable Long userId,
                                             @PathVariable Long puppyId,
                                             @PathVariable Long threadId,
                                             @PathVariable Long processId,
                                             @PathVariable Long reviewId,
                                             @RequestBody UpdateReviewRequest request){
        UpdateReviewResult result = reviewService.updateReview(
                userId,
                puppyId,
                threadId,
                processId,
                reviewId,
                request.toCommand());
        return UpdateReviewResponse.from(result);

    }

}
