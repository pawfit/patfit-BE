package com.peauty.payment.presentation;

import com.peauty.payment.business.PaymentService;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.CreateOrderResult;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackRequest;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackResponse;
import com.peauty.payment.presentation.dto.CreateOrderRequest;
import com.peauty.payment.presentation.dto.CreateOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Tag(name = "Payment", description = "Payment API")
@AllArgsConstructor
public class PaymentController {
    // TODO. customer에서 호출해서 사용하기, 컨트롤러를 통해 호출되는 것이 아닌 빈등록해서 메서드 호출하기
    private final PaymentService paymentService;

    @Operation(summary = "주문 저장", description = "결제하기 전에 주문 내역을 서버에 저장합니다.")
    @PostMapping("/users/{userId}/puppies/{puppyId}/processes/{processId}/threads/{threadId}/order")
    public CreateOrderResponse saveOrder(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId,
            @PathVariable Long threadId,
            @RequestBody CreateOrderRequest request) {
        CreateOrderResult createOrderResult = paymentService.
                saveOrder(
                        userId,
                        puppyId,
                        processId,
                        threadId,
                        request.toCommand());
        return CreateOrderResponse.from(createOrderResult);
    }

    @Operation(summary = "견적 수락", description = "디자이너가 제안한 견적을 수락합니다.")
    @PostMapping("/users/{userId}/puppies/{puppyId}/processes/{processId}/threads/{threadId}/payment")
    public CompletePaymentCallbackResponse acceptEstimate(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @PathVariable Long processId,
            @PathVariable Long threadId,
            @RequestBody CompletePaymentCallbackRequest request) {
        CompletePaymentCallbackResult result = paymentService.acceptEstimate(
                userId,
                puppyId,
                processId,
                threadId,
                request.toCommand());
        return CompletePaymentCallbackResponse.from(result);
    }
}
