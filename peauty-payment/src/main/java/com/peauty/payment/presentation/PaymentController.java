package com.peauty.payment.presentation;

import com.peauty.payment.business.PaymentService;
import com.peauty.payment.business.dto.CompletePaymentResult;
import com.peauty.payment.business.dto.OrderResult;
import com.peauty.payment.presentation.dto.CompletePaymentRequest;
import com.peauty.payment.presentation.dto.CompletePaymentResponse;
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
    // URL은 받되 유효성 검증은 좀 나중에 진행하자.
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
    public CompletePaymentResponse completePayment(
            @PathVariable Long userId,
            @PathVariable Long threadId,
            @PathVariable Long processId,
            @RequestBody CompletePaymentRequest request){
        CompletePaymentResult result = paymentService.completePayment(
                userId,
                threadId,
                processId,
                request.toCommand());
        return CompletePaymentResponse.from(result);
    }
}
