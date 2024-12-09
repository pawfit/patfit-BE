package com.peauty.payment.presentation;

import com.peauty.payment.business.PaymentService;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.OrderResult;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackRequest;
import com.peauty.payment.presentation.dto.CompletePaymentCallbackResponse;
import com.peauty.payment.presentation.dto.OrderRequest;
import com.peauty.payment.presentation.dto.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 주문 정보 API
    // TODO: 이런 URL을 가져가야 하는 것이 맞는가?
    @PostMapping("/users/{userId}/processes/{processId}/threads/{threadId}/order")
    public OrderResponse saveOrder(
            @PathVariable Long userId,
            @PathVariable Long threadId,
            @PathVariable Long processId,
            @RequestBody OrderRequest request){
        OrderResult orderResult = paymentService.saveOrder(
                userId,
                threadId,
                processId,
                request.toCommand());
        return OrderResponse.from(orderResult);
    }

    @PostMapping("/users/{userId}/processes/{processId}/threads/{threadId}/payment")
    public CompletePaymentCallbackResponse completePayment(
            @PathVariable Long userId,
            @PathVariable Long threadId,
            @PathVariable Long processId,
            @RequestBody CompletePaymentCallbackRequest request){
        CompletePaymentCallbackResult result = paymentService.completePayment(
                userId,
                threadId,
                processId,
                request.toCommand());
        return CompletePaymentCallbackResponse.from(result);
    }
}
