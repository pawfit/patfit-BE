package com.peauty.payment.presentation;

import com.peauty.payment.business.PaymentService;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.CreateOrderResult;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackRequest;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackResponse;
import com.peauty.payment.presentation.dto.CreateOrderRequest;
import com.peauty.payment.presentation.dto.CreateOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 주문 정보 API
    //
    // TODO: 이런 URL을 가져가야 하는 것이 맞는가?
    @PostMapping("/users/{userId}/processes/{processId}/threads/{threadId}/order")
    public CreateOrderResponse saveOrder(
            @PathVariable Long userId,
            @PathVariable Long processId,
            @PathVariable Long threadId,
            @RequestBody CreateOrderRequest request) {
        CreateOrderResult createOrderResult = paymentService.
                saveOrder(
                        userId,
                        processId,
                        threadId,
                        request.toCommand());
        return CreateOrderResponse.from(createOrderResult);
    }

    @PostMapping("/users/{userId}/processes/{processId}/threads/{threadId}/payment")
    public CompletePaymentCallbackResponse completePayment(
            @PathVariable Long userId,
            @PathVariable Long threadId,
            @PathVariable Long processId,
            @RequestBody CompletePaymentCallbackRequest request) {
        CompletePaymentCallbackResult result = paymentService.completePayment(
                userId,
                threadId,
                processId,
                request.toCommand());
        return CompletePaymentCallbackResponse.from(result);
    }
}
